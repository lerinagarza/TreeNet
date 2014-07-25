<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listGTINTableRelationship.jsp  ------------------------------------------//
//   To Be included in the listGTIN Page
//
//   Author :  Teri Walton  2/8/06   
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/30/08   TWalton     Changed Stylesheet to NEW Look
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqGTIN igTableRelationship = new InqGTIN();
 try
 {
	igTableRelationship = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewRelationship = request.getParameter("displayView");
 if (displayViewRelationship == null)
   displayViewRelationship = "";

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewRelationship + "&" +
                            igTableRelationship.buildParameterResend();
   String[] sortImage = new String[7];
   String[] sortStyle = new String[7];
   String[] sortOrder = new String[7];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "longDescription";
   sortOrder[2] = "tradeItemUnitDescriptor";
   sortOrder[3] = "qtyNextLowerLevelTradeItem";
   sortOrder[4] = "qtyChildren";
   sortOrder[5] = "qtyCompleteLayers";
   sortOrder[6] = "qtyItemsPerCompleteLayer";
  //************
  //Set Defaults
   for (int x = 0; x < 7; x++)
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
   for (int x = 0; x < 7; x++)
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
     <%= sortImage[1] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
      Long Description
     </a>      
    </td>
    <td class="td04120302" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Product Type
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      Total Quantity Of Next Lower Level Trade Item
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
       Quantity of Children
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Quantity Of Layers Contained In A Trade Item (HI)
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Quantity Of Trade Items Contained In A Complete Layer (TI)
     </a>      
    </td>   
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTableRelationship.getListReport() != null &&
      igTableRelationship.getListReport().size() > 0)
  {
    for (int x = 0; x < igTableRelationship.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTableRelationship.getListReport().elementAt(x);
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
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getQtyOfNextLowerLevelTradeItem() %>
    </td>  
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getQtyChildren() %>
    </td>  
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getQtyCompleteLayers() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getQtyItemsPerCompleteLayer() %>
    </td>  
    <td class="td04120102" style="text-align:right">
     <%= InqGTIN.buildMoreButton(igTableRelationship.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTableRelationship.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>