<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "java.util.Vector" %>
<%


//---------------- updGTIN.jsp -----------------------//
//   Author :  Teri Walton  8/30/2005
//   Changes: Replaces updUCCNet.jsp 
//             Author :  Charlena Paschen 07/02/2004
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//------------------------------------------------------------//
  String errorPage = "/GTIN/updGTIN.jsp";
   String updateTitle = "GTIN Update";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdGTIN updGTIN = new UpdGTIN();
 try
 {
	updGTIN = (UpdGTIN) request.getAttribute("updViewBean");
	if (updGTIN != null &&
	    updGTIN.getGtinNumber() != null &&
	    !updGTIN.getGtinNumber().equals(""))
	    updateTitle = updGTIN.getGtinNumber() + " : " + updateTitle;
	    Vector theseErrors = (Vector) updGTIN.getErrors();
	if (theseErrors != null &&
	    theseErrors.size() > 0)
  	  request.setAttribute("msg", ((String) theseErrors.elementAt(0)));
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",updateTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlGTIN?requestType=add\">Add New GTIN");
   setExtraOptions.append("<option value=\"/web/CtlGTIN\">Select List Again");
					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());         
   
//*****************************************************************************   
  // Set up the Dates to be Input on ALL JSP's within the ONE form Tag.
  request.setAttribute("date1Name", "effectiveDate");
  request.setAttribute("date1Function", "effDate");  
  request.setAttribute("date2Name", "publicationDate");
  request.setAttribute("date2Function", "pubDate");
  request.setAttribute("date4Name", "startAvailabilityDate");
  request.setAttribute("date4Function", "saDate");    
//--------------------------------------------------------
  String readOnly = "Y";
  String[] canUpdate = UpdGTIN.getSecurity(request, response);
  request.setAttribute("canUpdate", canUpdate);
  if (canUpdate[0].equals("Y") ||
      canUpdate[1].equals("Y"))
      readOnly = "N";
%>

<html>
 <head>
   <title><%= updateTitle %></title>
   <%= JavascriptInfo.getEditButton() %>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 1, "Y", 8) %>   
   <%= JavascriptInfo.getCalendarHead() %>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
 </head>
 <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
<table class="table00" cellspacing="0" style="width:100%">
 <tr>
  <td class="td04140105">&nbsp;</td> 
<%
//   Add this section if you want to select a new one ON this page.
%>   
  <td class="td04140105">
<form method="link" action="#">
    <input type="submit" value="Select New GTIN"
        onClick="ANY_NAME=window.open('/web/APP/GTIN/selGTIN.jsp?requestType=update&location=/web/CtlGTIN', 'win1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width=300,height=300'); return false;">
  </td>
   <%= HTMLHelpersInput.endForm() %>
  <td class="td04140105" style="width:3%">
   &nbsp;
  </td>          
 </tr>
</table>
<table class="table00" cellspacing="0" cellpadding="3">
  <form name = "updGTIN" action="/web/CtlGTIN?requestType=<%= updGTIN.getRequestType() %>" method="post">

 <tr>
  <td style="width:2%">&nbsp;</td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
     <tr class="tr02">
     <td class="td0320" colspan = "2"><b>&nbsp;&nbsp;Global Trade Item Number</b></td>
     <td colspan = "2"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></td>
    </tr>  
   <tr>
     <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102" style="width:30%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Global_Trade_Item_Number" 
	     target="_blank" title="Click here to see help documents.">Global Trade Item Number (GTIN):</a>
	 </td>
	 <td class="td04140102">&nbsp;
<%
   if (updGTIN.getRequestType().equals("update"))
   {
     out.println(HTMLHelpersLinks.routerGTIN(updGTIN.getGtinNumber(), "a0412", "", ""));
     out.println(HTMLHelpersInput.inputBoxHidden("gtinNumber", updGTIN.getGtinNumber()));
   }  
   else
   {
%>   
      <%= HTMLHelpersInput.inputBoxText("gtinA", updGTIN.getGtinA(), "Gtin Number", 2, 2, "Y", readOnly) %>&nbsp;
      <%= HTMLHelpersInput.inputBoxText("gtinB", updGTIN.getGtinB(), "Gtin Number", 6, 6, "Y", readOnly) %>&nbsp;
      <%= HTMLHelpersInput.inputBoxText("gtinC", updGTIN.getGtinC(), "Gtin Number", 5, 5, "Y", readOnly) %>&nbsp;
      <%= HTMLHelpersInput.inputBoxText("gtinD", updGTIN.getGtinD(), "Gtin Number", 1, 1, "N", "Y") %>&nbsp;
<%      
   }
%>  
	   <font style="color:#990000">* Required </font><%= updGTIN.getGtinNumberError() %>
	 </td>
	 <td class="td04140102" style="width:2%">&nbsp;</td>
<% 
  int imageCount  = 3;
  int expandCount = 1;
  request.setAttribute("imageCount", new Integer(imageCount));
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
       <%= JavascriptInfo.getExpandingSection("O", "General Setup", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
       <jsp:include page="updGTINGreen.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>
<% 
  imageCount = ((Integer) request.getAttribute("imageCount")).intValue();
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
      <%= JavascriptInfo.getExpandingSection("C", "Physical Specifications", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
      <jsp:include page="updGTINOrange.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>   
     </td>
    </tr>   
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
      <%= JavascriptInfo.getExpandingSection("C", "Item & Classification", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
      <jsp:include page="updGTINBlue.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr> 
<% 
  imageCount++;
  expandCount++;
  request.setAttribute("imageCount", new Integer(imageCount));
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
      <%= JavascriptInfo.getExpandingSection("C", "Images", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="../Utilities/displayIFSNew.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %> 
     </td>
    </tr> 
<% 
  imageCount = ((Integer) request.getAttribute("imageCount")).intValue();
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("imageCount", new Integer(imageCount));
  request.setAttribute("listKeyValues", updGTIN.getListAllURLs());
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
      <%= JavascriptInfo.getExpandingSection("C", "Additional Images and Documents", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
      <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>   
     </td>
    </tr>            
<% 
  imageCount = ((Integer) request.getAttribute("imageCount")).intValue();
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", updGTIN.getListAllComments());  
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
      <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
      <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>  
     </td>
    </tr>  
<%
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td class="td03141405" colspan = "4">
      <%= JavascriptInfo.getExpandingSection("C", "Additional Information", 0, expandCount, imageCount, 1, 0) %>
 	   <table class="table00" cellspacing="0" cellpadding="2">
        <tr>
         <td class="td04140102" style="width:2%">&nbsp;</td>
         <td class="td04140102" style="width:30%"><b>
          Last Change Date:
          </b>
         </td>
         <td class="td04140102">&nbsp;
          <%= updGTIN.getLastChangeDate() %> 
         </td>  
         <td class="td04140102" style="width:2%">&nbsp;</td>
        </tr> 
        <tr>
         <td class="td04140102">&nbsp;</td>
         <td class="td04140102"><b>
          Last Update User:
          </b>
         </td>
         <td class="td04140102">&nbsp;
          <%= updGTIN.getLastUpdateUser() %> 
         </td>
         <td class="td04140102">&nbsp;</td>
        </tr>   
        <tr>
         <td class="td04140102">&nbsp;</td>
         <td class="td04140102"><b>
          Last Update Date and Time:
          </b>
         </td>
         <td class="td04140102">&nbsp;
          <%= updGTIN.getLastUpdateDate() %>&nbsp;&nbsp;&nbsp;<%= HTMLHelpersMasking.maskTime(updGTIN.getLastUpdateTime()) %>
         </td>
         <td class="td04140102">&nbsp;</td>
        </tr>   
       </table> 
      <%= HTMLHelpers.endSpan() %> 
     </td>
    </tr>   
  </table>
  </td>
  <td></td>
 </tr>  
<%= HTMLHelpersInput.endForm() %>
</table> 
  <%= JavascriptInfo.getCalendarFoot("updGTIN", (String) request.getAttribute("date1Function"), (String) request.getAttribute("date1Name")) %> 
  <%= JavascriptInfo.getCalendarFoot("updGTIN", (String) request.getAttribute("date2Function"), (String) request.getAttribute("date2Name")) %> 
  <%= JavascriptInfo.getCalendarFoot("updGTIN", (String) request.getAttribute("date4Function"), (String) request.getAttribute("date4Name")) %>      
  <%@ include file="../../Include/footer.jsp" %>
   </body>
</html>