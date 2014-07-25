<%@ page language = "java" %>
<%@ page import = "com.treetop.app.item.UpdItemVariance" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanItem" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "java.util.*" %>
<%


//------------------ updVarianceTTSpecs.jsp ------------------------------//
//
// Author :  Teri Walton  9/13/02                                         
//   moved to Production 12/19/02
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  9/12/08    TWalton	   Redo Process - using Servlet and new Stylesheet
//  2/25/04    TWalton     Changed comments and images for 5.0 server.
//------------------------------------------------------------------------//
 String errorPage = "Item/updItemVariance.jsp";
 String updTitle = "Update Variance";  
 // Bring in the Build View Bean.
 // Selection Criteria
 String returnMessage = "";
 UpdItemVariance uv = new UpdItemVariance();
 String readOnly = "Y";
 try
 {
	uv = (UpdItemVariance) request.getAttribute("updViewBean");
	if (uv.getRecordStatus().trim().equals("PENDING"))
	  readOnly = "N";
//	bi = (BeanItem) ui.getinqItem.getListReport().elementAt(0);
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
   request.setAttribute("title",updTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlItem?requestType=inqVariance\">Choose Again");
   setExtraOptions.append("<option value=\"/web/CtlItem?requestType=listVariance&inqItem=" + uv.getItem().trim() + "&showPending=Y\">Go Back to List");
   request.setAttribute("extraOptions", setExtraOptions.toString());      
//*****************************************************************************
%>
<html>
 <head>
  <title><%= updTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
   <%= JavascriptInfo.getCalendarHead() %>
   <%= JavascriptInfo.getCheckTextareaLength() %>
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form  name = "itemVariance" action="/web/CtlItem?requestType=updVariance" method="post">
    <%= HTMLHelpersInput.inputBoxHidden("updDate", uv.getUpdDate()) %>
    <%= HTMLHelpersInput.inputBoxHidden("updTime", uv.getUpdTime()) %>
    <%= HTMLHelpersInput.inputBoxHidden("updUser", uv.getUpdUser()) %>
    <%= HTMLHelpersInput.inputBoxHidden("originalDateIssued", uv.getOriginalDateIssued()) %>
    <%= HTMLHelpersInput.inputBoxHidden("originalDateExpired", uv.getOriginalDateExpired() ) %>
    <%= HTMLHelpersInput.inputBoxHidden("showPending", "Y") %>
    <%= HTMLHelpersInput.inputBoxHidden("inqItem", uv.getItem()) %>
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!uv.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= uv.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
       <tr class="tr02">
        <td class="td0418" style="width:5%">&nbsp;</td>
        <td class="td0418" colspan="2">
         <b> Update Variance</b>
        </td>
        <td class="td0418">&nbsp;
<%
   if (readOnly.equals("N"))
      out.println( HTMLHelpers.buttonSubmit("goButton", "Go - Update Variance"));
   else
       out.println("Must be in Pending Status to Update");
%>          
        </td>
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr> 
       <tr>
        <td class="td0414" style="text-align:right"><b>Item :  </b></td>
        <td class="td0414">
         <b><%= uv.getItem() %></b>&nbsp;&nbsp;-&nbsp;&nbsp;<%= uv.getItemDescription() %>
         <%= HTMLHelpersInput.inputBoxHidden("item", uv.getItem()) %>
        </td>
       </tr>
       <tr>
        <td class="td0414" style="text-align:right"><b>Status :  </b></td>
        <td class="td0414"> 
        <%= UpdItemVariance.buildDropDownStatus(uv.getRecordStatus(), readOnly) %>
        </td>
       </tr>      
       <tr>
        <td class="td0414" style="text-align:right"><b>Issue Date :  </b></td>
        <td class="td0414">
<%
   String dateIssued = uv.getDateIssued();
   try
   {
      if (!dateIssued.trim().equals("0"))
      {
         DateTime dtIssued = UtilityDateTime.getDateFromyyyyMMdd(uv.getDateIssued());
         dateIssued = dtIssued.getDateFormatMMddyyyySlash();
      }
   }
   catch(Exception e)
   {}
%>        
        <%= HTMLHelpersInput.inputBoxDate("dateIssued", dateIssued, "cal1", "N", readOnly) %>
        </td>
       </tr>
       <tr>
        <td class="td0414" style="text-align:right"><b>Expire Date :  </b></td>
        <td class="td0414">
<%
   String dateExpired = uv.getDateExpired();
   try
   {
      if (!dateExpired.trim().equals("0"))
      {
         DateTime dtExpired = UtilityDateTime.getDateFromyyyyMMdd(uv.getDateExpired());
         dateExpired = dtExpired.getDateFormatMMddyyyySlash();
      }
   }
   catch(Exception e)
   {}
%>               
        <%= HTMLHelpersInput.inputBoxDate("dateExpired", dateExpired, "cal2", "N", readOnly) %>
        </td>
       </tr>
       <tr>
        <td class="td0414" style="text-align:right" valign="top"><b>Variance :  </b></td>
        <td colspan="2">
         <%= HTMLHelpersInput.inputBoxTextarea("varianceText", uv.getVarianceText(), 7, 75, 512, readOnly) %>
        </td>
       </tr>
       <tr>
        <td class="td0414" style="text-align:right" valign="top"><b>Comment :  </b></td>
        <td colspan="2">
         <%= HTMLHelpersInput.inputBoxTextarea("varianceComment", uv.getVarianceText(), 3, 75, 512, readOnly) %>
        </td>
       </tr>        
      </table>    
      <%= JavascriptInfo.getCalendarFoot("itemVariance", "cal1", "dateIssued") %> 
      <%= JavascriptInfo.getCalendarFoot("itemVariance", "cal2", "dateExpired") %> 
     <td style="width:2%">&nbsp;</td>      
    </tr>
   </table>
  </form>  
  <br>  
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>