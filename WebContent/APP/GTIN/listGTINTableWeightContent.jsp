<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listGTINTableWeightCntent.jsp  ------------------------------------------//
//   To Be included in the listGTIN Page
//
//   Author :  Teri Walton  2/7/06   
//    Date        Name      Comments
//  ---------   --------   -------------
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqGTIN igTableWeightContent = new InqGTIN();
 try
 {
	igTableWeightContent = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewWeightContent = request.getParameter("displayView");
 if (displayViewWeightContent == null)
   displayViewWeightContent = "";

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewWeightContent + "&" +
                            igTableWeightContent.buildParameterResend();
   String[] sortImage = new String[8];
   String[] sortStyle = new String[8];
   String[] sortOrder = new String[8];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "longDescription";
   sortOrder[2] = "netContent";
   sortOrder[3] = "netContentUnits";
   sortOrder[4] = "netWeight";
   sortOrder[5] = "grossWeight";
   sortOrder[6] = "weightUnits";
   sortOrder[7] = "tradeItemUnitDescriptor";
   
  //************
  //Set Defaults
   for (int x = 0; x < 8; x++)
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
   for (int x = 0; x < 8; x++)
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
     <%= sortImage[7] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      Trade Item Unit Descriptor
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Net Content
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      Net Content Units (UOM)
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
       Net Weight
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Gross Weight
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Weight Units (UOM)
     </a>      
    </td>   
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTableWeightContent.getListReport() != null &&
      igTableWeightContent.getListReport().size() > 0)
  {
    for (int x = 0; x < igTableWeightContent.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTableWeightContent.getListReport().elementAt(x);
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
     &nbsp;<%= thisrow.getGtinDetail().getNetContent() %>
    </td> 
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getNetContentUnitOfMeasure() %>&nbsp;
    </td>  
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getNetWeight() %>
    </td>  
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getGrossWeight() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getWeightUnitOfMeasure() %>&nbsp;
    </td>  
    <td class="td04120102" style="text-align:center">
     <%= InqGTIN.buildMoreButton(igTableWeightContent.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTableWeightContent.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>