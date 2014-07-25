<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 

//---------------  listGTINTable.jsp  ------------------------------------------//
//   To Be included in the listGTIN Page
//
//   Author :  Teri Walton  8/30/05   
//   Changes:  // Converted from 
//                listUCCnet.jsp  Author :  Charlena Paschen 06/03/04
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqGTIN igTable = new InqGTIN();
 try
 {
	igTable = (InqGTIN) request.getAttribute("inqViewBean");
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

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewStandard + "&" +
                            igTable.buildParameterResend();
   String[] sortImage = new String[13];
   String[] sortStyle = new String[13];
   String[] sortOrder = new String[13];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "tradeItemDescription";
   sortOrder[2] = "resourceNumber";
   sortOrder[3] = "tradeItemUnitDescriptor";
   sortOrder[4] = "brandName";
   sortOrder[5] = "longDescription";
   sortOrder[6] = "isBaseUnit";
   sortOrder[7] = "load";
   sortOrder[8] = "isConsumerUnit";
   sortOrder[9] = "isOrderableUnit";
   sortOrder[10] = "isDispatchUnit";
   sortOrder[11] = "isInvoiceUnit";
   sortOrder[12] = "lastUpdateDate";
  //************
  //Set Defaults
   for (int x = 0; x < 13; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
   String orderBy = request.getParameter("orderBy");
   if (orderBy == null)
      orderBy = "gtinNumber";
   String orderStyle = request.getParameter("orderStyle");
   if (orderStyle == null)
      orderStyle = "";
   for (int x = 0; x < 13; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (orderStyle.equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowUpDark.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowDownDark.gif\">";
     }
   }   
%>

<html>
  <head>
     <%= JavascriptInfo.getMoreButton() %>
  </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
  // HEADING SECTION
%>  
   <tr class="tr02">
    <td class="td04120302" style="text-align:center">
     <%= sortImage[0] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
      GTIN Number
     </a>      
    </td>
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Long Description
     </a>      
    </td>
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      Trade Item Unit Descriptor
     </a>      
    </td>      
    <td class="td014CC001" style="border-right:1px solid #CCCC99">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Item
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      Brand
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Is<br>Base<br>Unit?
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[8] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[8] %>&orderStyle=<%= sortStyle[8] %>">
      Is<br>Consumer<br>Unit?
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[9] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[9] %>&orderStyle=<%= sortStyle[9] %>">
      Is<br>Orderable<br>Unit?
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[10] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[10] %>&orderStyle=<%= sortStyle[10] %>">
      Is<br>Dispatch<br>Unit?
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[11] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[11] %>&orderStyle=<%= sortStyle[11] %>">
      Is<br>Invoice<br>Unit?
     </a>      
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= sortImage[7] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      Load
     </a>      
    </td>                        
    <td class="td04120302" style="text-align:center">
     <%= sortImage[12] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[12] %>&orderStyle=<%= sortStyle[12] %>">
      Last<br>Update<br>Date
     </a>      
    </td>   
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTable.getListReport() != null &&
      igTable.getListReport().size() > 0)
  {
    for (int x = 0; x < igTable.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTable.getListReport().elementAt(x);
%>  
   <tr class="tr00">
    <td class="td04120302">
     <%= HTMLHelpersLinks.routerGTIN(thisrow.getGtinDetail().getGtinNumber(), "a0412", "", "") %>
    </td>
    <td class="td04120302">
     &nbsp;<%= thisrow.getGtinDetail().getGtinLongDescription() %>
    </td> 
    <td class="td04120302">
     &nbsp;<%= thisrow.getGtinDetail().getTradeItemUnitDescriptor() %>
    </td> 
    <td class="td04120302">&nbsp;
<%
   if (thisrow.getItem() != null &&
       thisrow.getItem().getItemNumber() != null &&
       !thisrow.getItem().getItemNumber().equals(""))
     out.println(HTMLHelpersLinks.routerItem(thisrow.getItem().getItemNumber(), "a0412", "", ""));
   // Take out the link to the Router
%>    
    </td>   
    <td class="td04120302">
     &nbsp;<%= thisrow.getGtinDetail().getBrandName() %>
    </td> 
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsBaseUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsConsumerUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsOrderableUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsDispatchUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsInvoiceUnit() %>
    </td>                  
    <td class="td04120302" style="text-align:center">
<%  
   if (thisrow.getGtinDetail().getPublishToUCCNet().equals("Y"))
      out.println("Yes");
   else
   {
      if (thisrow.getGtinDetail().getPublishToUCCNet().equals("N"))
        out.println("No");
      else
        out.println("Pending");
   }   
%>    
    </td>    
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getLastUpdateDate() %>
    </td>   
    <td class="td04120102" style="text-align:right">
     <%= InqGTIN.buildMoreButton(igTable.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTable.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>