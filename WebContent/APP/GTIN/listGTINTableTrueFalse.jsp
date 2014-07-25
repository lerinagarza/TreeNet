<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listGTINTableTrueFalse.jsp  ------------------------------------------//
//   To Be included in the listGTIN Page
//
//   Author :  Teri Walton  2/8/06   
//    Date        Name      Comments
//  ---------   --------   -------------
//------------------------------------------------------//
//  5/30/08   TWalton     Changed Stylesheet to NEW Look
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqGTIN igTableTF = new InqGTIN();
 try
 {
	igTableTF = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewTF = request.getParameter("displayView");
 if (displayViewTF == null)
   displayViewTF = "";

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewTF + "&" +
                            igTableTF.buildParameterResend();
   String[] sortImage = new String[18];
   String[] sortStyle = new String[18];
   String[] sortOrder = new String[18];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "longDescription";
   sortOrder[2] = "isConsumerUnit";
   sortOrder[3] = "isOrderableUnit";
   sortOrder[4] = "isBaseUnit";
   sortOrder[5] = "isDispatchUnit";
   sortOrder[6] = "isInvoiceUnit";
   sortOrder[7] = "isVariableUnit";
   sortOrder[8] = "isRecyclable";
   sortOrder[9] = "isReturnable";
   sortOrder[10] = "hasExpireDate";
   sortOrder[11] = "hasGreenDot";
   sortOrder[12] = "hasIngredients";
   sortOrder[13] = "isNetContentDeclarationIndicated";
   sortOrder[14] = "hasBatchNumber";
   sortOrder[15] = "isNonSoldReturnable";
   sortOrder[16] = "isItemRecyclable";   
   sortOrder[17] = "tradeItemUnitDescriptor";
   
  //************
  //Set Defaults
   for (int x = 0; x < 18; x++)
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
   for (int x = 0; x < 18; x++)
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
     <%= sortImage[17] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[17] %>&orderStyle=<%= sortStyle[17] %>">
      Trade Item Unit Descriptor
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Is A Consumer Unit
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
       Is An Orderable Unit
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      Is A Base Unit
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Is A Dispatch Unit
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Is An Invoice Unit
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[7] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      Is A Variable Unit
     </a>      
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= sortImage[8] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[8] %>&orderStyle=<%= sortStyle[8] %>">
      Is Packaging Marked As Recyclable
     </a>      
    </td>                        
    <td class="td04120302" style="text-align:center">
     <%= sortImage[9] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[9] %>&orderStyle=<%= sortStyle[9] %>">
      Is Packaging Marked As Returnable
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[10] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[10] %>&orderStyle=<%= sortStyle[10] %>">
      Is Packaging Marked With Expiration Date
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[11] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[11] %>&orderStyle=<%= sortStyle[11] %>">
      Is Packaging Marked With Green Dot
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[12] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[12] %>&orderStyle=<%= sortStyle[12] %>">
      Is Packaging Marked With Ingredients
     </a>      
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= sortImage[13] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[13] %>&orderStyle=<%= sortStyle[13] %>">
      Is Net Content Declaration Indicated
     </a>      
    </td>                        
    <td class="td04120302" style="text-align:center">
     <%= sortImage[14] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[14] %>&orderStyle=<%= sortStyle[14] %>">
      Has Batch Number
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[15] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[15] %>&orderStyle=<%= sortStyle[15] %>">
      Is Non Sold Trade Item Returnable
     </a>      
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= sortImage[16] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[16] %>&orderStyle=<%= sortStyle[16] %>">
      Is Trade Item Marked as Recyclable
     </a>      
    </td>              
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTableTF.getListReport() != null &&
      igTableTF.getListReport().size() > 0)
  {
    for (int x = 0; x < igTableTF.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTableTF.getListReport().elementAt(x);
%>  
   <tr class="tr00001">
    <td class="td04120302">
     <%= HTMLHelpersLinks.routerGTIN(thisrow.getGtinDetail().getGtinNumber(), "a0412", "", "") %>
    </td>
    <td class="td04120302">
     &nbsp;<%= thisrow.getGtinDetail().getGtinLongDescription() %>
    </td> 
    <td class="td04120302">
     &nbsp;<%= thisrow.getGtinDetail().getTradeItemUnitDescriptor() %>
    </td> 
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsConsumerUnit() %>
    </td> 
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsOrderableUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsBaseUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsDispatchUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsInvoiceUnit() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsVariableUnit() %>
    </td>                  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsPackagingMarkedRecyclable() %>
    </td>    
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsPackagingMarkedReturnable() %>
    </td>   
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsPackagingMarkedWithExpirationDate() %>
    </td>   
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsPackagingMarkedWithGreenDot() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsPackagingMarkedWithIngredients() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsNetContentDeclarationIndicated() %>
    </td>                  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getHasBatchNumber() %>
    </td>    
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsNonSoldReturnable() %>
    </td>    
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsItemMarkedRecyclable() %>
    </td>   
    <td class="td04120302" style="text-align:right">
     <%= InqGTIN.buildMoreButton(igTableTF.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTableTF.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>