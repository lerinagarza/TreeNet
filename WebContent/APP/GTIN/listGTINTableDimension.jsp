<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listGTINTableDimension.jsp  ------------------------------------------//
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
 InqGTIN igTableDim = new InqGTIN();
 try
 {
	igTableDim = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewDim = request.getParameter("displayView");
 if (displayViewDim == null)
   displayViewDim = "";

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewDim + "&" +
                            igTableDim.buildParameterResend();
   String[] sortImage = new String[9];
   String[] sortStyle = new String[9];
   String[] sortOrder = new String[9];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "longDescription";
   sortOrder[2] = "height";
   sortOrder[3] = "width";
   sortOrder[4] = "depth";
   sortOrder[5] = "linearUnits";
   sortOrder[6] = "volume";
   sortOrder[7] = "volumeUnits";
   sortOrder[8] = "tradeItemUnitDescriptor";
   
  //************
  //Set Defaults
   for (int x = 0; x < 9; x++)
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
   for (int x = 0; x < 9; x++)
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
     <%= sortImage[8] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[8] %>&orderStyle=<%= sortStyle[8] %>">
      Trade Item Unit Descriptor
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Height
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      Width
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
       Depth
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Linear Units (UOM)
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Volume
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[7] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      Volume Units (UOM)
     </a>      
    </td>   
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTableDim.getListReport() != null &&
      igTableDim.getListReport().size() > 0)
  {
    for (int x = 0; x < igTableDim.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTableDim.getListReport().elementAt(x);
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
     &nbsp;<%= thisrow.getGtinDetail().getHeight() %>
    </td> 
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getWidth() %>
    </td>  
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getDepth() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getLinearUnitOfMeasure() %>&nbsp;
    </td>  
    <td class="td04120302" style="text-align:right">
     <%= thisrow.getGtinDetail().getVolume() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getVolumeUnitOfMeasure() %>&nbsp;
    </td>                  
    <td class="td04120102" style="text-align:right">
     <%= InqGTIN.buildMoreButton(igTableDim.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTableDim.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>