<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listGTINTableMisc.jsp  ------------------------------------------//
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
 InqGTIN igTableMisc = new InqGTIN();
 try
 {
	igTableMisc = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewMisc = request.getParameter("displayView");
 if (displayViewMisc == null)
   displayViewMisc = "";

   String columnHeadingTo = "/web/CtlGTIN?requestType=list&" +
                            "displayView=" + displayViewMisc + "&" +
                            igTableMisc.buildParameterResend();
   String[] sortImage = new String[11];
   String[] sortStyle = new String[11];
   String[] sortOrder = new String[11];
   sortOrder[0] = "gtinNumber";
   sortOrder[1] = "longDescription";
   sortOrder[2] = "tradeItemEffectiveDate";
   sortOrder[3] = "catalogueItemState";
   sortOrder[4] = "countryOfOrigin";
   sortOrder[5] = "isInformationPrivate";
   sortOrder[6] = "targetMarketCountryCode";
   sortOrder[7] = "publicationDate";
   sortOrder[8] = "load";
   sortOrder[9] = "startAvailabilityDate";
   sortOrder[10] = "tradeItemUnitDescriptor";   
  //************
  //Set Defaults
   for (int x = 0; x < 11; x++)
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
   for (int x = 0; x < 11; x++)
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
     <%= sortImage[10] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[10] %>&orderStyle=<%= sortStyle[10] %>">
      Trade Item Unit Descriptor
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Effective Date
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
       Catalogue Item State
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      Trade Item Country Of Origin
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      Is Information Private
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      Target Market
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <%= sortImage[7] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      Publication Date
     </a>      
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= sortImage[8] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[8] %>&orderStyle=<%= sortStyle[8] %>">
      Load
     </a>      
    </td>   
    <td class="td04120302" style="text-align:center">
     <acronym title="How should this be loaded to UCCNet"">How Loaded?</acronym>      
    </td>                          
    <td class="td04120302" style="text-align:center">
     <%= sortImage[9] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[9] %>&orderStyle=<%= sortStyle[9] %>">
      Start Availability Date
     </a>      
    </td>   
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (igTableMisc.getListReport() != null &&
      igTableMisc.getListReport().size() > 0)
  {
    for (int x = 0; x < igTableMisc.getListReport().size(); x++)
    {
      BeanGTIN thisrow = (BeanGTIN) igTableMisc.getListReport().elementAt(x);
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
     &nbsp;<%= thisrow.getGtinDetail().getEffectiveDate() %>
    </td> 
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getCatalogItemState() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getCountryOfOrigin() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getIsInformationPrivate() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getGtinDetail().getTargetMarketCountryCode() %>
    </td>  
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getPublicationDate() %>
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
<%  
   if (thisrow.getGtinDetail().getStatus().trim().equals("M"))
     out.println("Modify");
   if (thisrow.getGtinDetail().getStatus().trim().equals("A"))
     out.println("Add");
%>    
    </td>        
    <td class="td04120302" style="text-align:center">
     <%= thisrow.getGtinDetail().getStartAvailabilityDate() %>
    </td>   
    <td class="td04120302" style="text-align:right">
     <%= InqGTIN.buildMoreButton(igTableMisc.getRequestType(), thisrow.getGtinDetail().getGtinNumber(), igTableMisc.buildParameterResend()) %>
    <td>
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>