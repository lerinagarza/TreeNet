<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listGTINTableDescription.jsp  ------------------------------------------//
//   To Be included in the listGTIN Page
//
//   Author :  Teri Walton  2/7/06   
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
 InqGTIN igTableDesc = new InqGTIN();
 try
 {
	igTableDesc = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }   
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewDesc = request.getParameter("displayView");
 if (displayViewDesc == null)
   displayViewDesc = "";

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewDesc + "&" +
                            igTableDesc.buildParameterResend();
   String[] sortImage = new String[13];
   String[] sortStyle = new String[13];
   String[] sortOrder = new String[13];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "longDescription";
   sortOrder[2] = "resourceNumber";
   sortOrder[3] = "eanUCCCode";
   sortOrder[4] = "eanUCCType";
   sortOrder[5] = "gtinDescription";
   sortOrder[6] = "additionalTradeItemDescription";
   sortOrder[7] = "functionalName";
   sortOrder[8] = "brandName";
   sortOrder[9] = "classificationCategoryCode";
   sortOrder[10] = "informationProviderName";
   sortOrder[11] = "informationProvider";
   sortOrder[12] = "tradeItemUnitDescriptor";
   
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
     <%= sortImage[1] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
      GTIN Name
     </a>      
    </td>
    <td class="td04120302" style="text-align:center">
     <%= sortImage[12] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[12] %>&orderStyle=<%= sortStyle[12] %>">
      Trade Item Unit Descriptor
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      EANUCC Code
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
       EANUCC Type
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Short Description
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Additional Description
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[7] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      Functional Name
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[8] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[8] %>&orderStyle=<%= sortStyle[8] %>">
      Brand Name
     </a>      
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= sortImage[9] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[9] %>&orderStyle=<%= sortStyle[9] %>">
      Alternate Item Classification Code
     </a>      
    </td>                        
    <td class="td04120302" style="text-align:center">
     <%= sortImage[10] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[10] %>&orderStyle=<%= sortStyle[10] %>">
      Brand Owner Name
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[11] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[11] %>&orderStyle=<%= sortStyle[11] %>">
      Brand Owner
     </a>      
    </td>   
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTableDesc.getListReport() != null &&
      igTableDesc.getListReport().size() > 0)
  {
    for (int x = 0; x < igTableDesc.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTableDesc.getListReport().elementAt(x);
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
    <td class="td04120302">
     &nbsp;<%= thisrow.getGtinDetail().getEanUCCCode() %>
    </td> 
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getEanUCCType() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getGtinDescription() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getAdditionalTradeItemDescription() %>&nbsp;
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getFunctionalName() %>&nbsp;
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getBrandName() %>&nbsp;
    </td>                  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getClassificationCategoryCode() %>&nbsp;
    </td>    
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getNameOfInformationProvider() %>
    </td>   
   <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getInformationProvider() %>
    </td>   
    <td class="td04120102" style="text-align:right">
     <%= InqGTIN.buildMoreButton(igTableDesc.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTableDesc.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>