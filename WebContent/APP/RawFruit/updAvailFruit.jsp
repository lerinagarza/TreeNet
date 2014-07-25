<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.InqAvailableFruit" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- updAvailFruit.jsp ------------------------------//
//  Author :  Teri Walton  09/02/10
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Choose Warehouse and Location for Update Available Fruit By Warehouse Location";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqAvailableFruit inqAvail = new InqAvailableFruit();
 try
 {
	inqAvail = (InqAvailableFruit) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
   System.out.println("should never get here, updAvailFruit.jsp: " + e);
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   Vector headerInfo = new Vector();
    headerInfo.addElement(inqAvail.getEnvironment());
    headerInfo.addElement(updTitle); // Element 1 is always the Title of the Page
    
//   StringBuffer setExtraOptions = new StringBuffer();
//   if (ir.getAllowUpdate().equals("Y"))
//      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a Load");
//   setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=inqFormula\">Select Again");
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket&");
//   setExtraOptions.append(ir.buildParameterResend());
//   setExtraOptions.append("\">Return To Selection Screen");
//   if (!is.getAllowUpdate().equals(""))   
//     setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=addCPGSpec\">Add New Specification");

//   headerInfo.addElement(setExtraOptions.toString());
//*****************************************************************************
 String formName = "updAvail";
 request.setAttribute("formName", formName);
%>
<html>
 <head>
  <title><%= updTitle %></title>
 
<%
   if (inqAvail.getTripleDropDownRegion().size() == 5)
   {
      out.println(inqAvail.getTripleDropDownRegion().elementAt(0));
      out.println(inqAvail.getTripleDropDownRegion().elementAt(1));
   }
%>  
 <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getClickButtonOnlyOnce() %>
  <%= JavascriptInfo.getChangeSubmitButton() %>   
 </head>
 <body>
  <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
  <form  name = "<%= formName %>" action="/web/CtlRawFruit?requestType=updAvailFruit" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", inqAvail.getEnvironment()) %> 
 <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!inqAvail.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= inqAvail.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
  <tr>
   <td class="td0414" rowspan="2" style="width:10%">&nbsp;</td>
   <td class="td0424" colspan="2"><b>Choose a Warehouse and Location:</b></td>
   <td class="td0414" style="text-align:right"><%= HTMLHelpers.buttonSubmit("goButton", "Go to Update") %></td>
   <td class="td0414" rowspan="2"style="width=10%">&nbsp;</td>
  </tr>
  <tr>
<%
   if (inqAvail.getTripleDropDownRegion().size() == 5)
   {
%>   
    <td class="td0324" colspan="3">
      &nbsp;&nbsp;<%= (String) inqAvail.getTripleDropDownRegion().elementAt(2) %><br>
      &nbsp;&nbsp;<%= (String) inqAvail.getTripleDropDownRegion().elementAt(3) %><br>
      &nbsp;&nbsp;<%= (String) inqAvail.getTripleDropDownRegion().elementAt(4) %>
    </td>      
<%
   }else{
%>
    <td class="td0424" style="width:25%"><b>
      <%= inqAvail.getWhseNo() %>&nbsp;&nbsp;-&nbsp;&nbsp;<%= inqAvail.getWhseDescription() %>&nbsp;&nbsp;
      <%= HTMLHelpersInput.inputBoxHidden("whseNo", inqAvail.getWhseNo()) %>
     </b> 
    </td>
    <td class="td0424" colspan="2"><%= inqAvail.getSingleDropDownLocation()%></td>  
    <td class="td0424">&nbsp;</td>  
<%      
   }
%>   
  </tr>  
 </table>

 </form>
 <%= HTMLHelpers.pageFooterTable(inqAvail.getRequestType()) %>

   </body>
</html>