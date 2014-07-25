<%@ page language="java" %>
<%@ page import = "com.treetop.app.transaction.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Quality/inqTrackAndTrace.jsp -----------------------//
// Author   :  John Hagler       2011-05-05 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
 
 
 
 
 // ************  WHAT'S THIS???  ************************
  String errorPage = "Quality/inqMethod.jsp";
 
  String inqTitle = "Call Track and Trace Program";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqTransaction it = new InqTransaction();
 try {
	it = (InqTransaction) request.getAttribute("inqViewBean");
	
	if (it.getRequestType().equals("")) {
	   it.setRequestType("inqSingleIngredientForward");
	}
	   
	if (it.getRequestType().trim().equals("inqSingleIngredientForward")) {
		inqTitle = "Track and Trace:  Single Ingredient Forward to Shipping";
	}
	if (it.getRequestType().trim().equals("inqProductionDayBack")) {
		inqTitle = "Track and Trace:  Production Day Back to Ingredients";
	}
	if (it.getRequestType().trim().equals("inqProductionDayForward")) {
		inqTitle = "Track and Trace:  Production Day Forward to Shipping";
	}
	if (it.getRequestType().trim().equals("inqFruitToShipping")) {
		inqTitle = "Track and Trace:  Fruit to Shipping - Fresh Slice";
	}
 }
 catch(Exception e) {}
// finally {}
 //{
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 //}  
//**************************************************************************//
  // Allows the Title to display in the Top Part of the Page
  Vector headerInfo = new Vector();
  headerInfo.addElement(it.getInqEnvironment());
  headerInfo.addElement(inqTitle); // Element 0 is always the Title of the Page
 
   StringBuffer setExtraOptions = new StringBuffer();
 
     setExtraOptions.append("<option value=\"/web/CtlTransaction?requestType=inqSingleIngredientForward\">Single Ingredient Forward to Shipping");
     setExtraOptions.append("<option value=\"/web/CtlTransaction?requestType=inqProductionDayBack\">Production Day Back to Ingredients");
     setExtraOptions.append("<option value=\"/web/CtlTransaction?requestType=inqProductionDayForward\">Production Day Forward to Shipping");
     setExtraOptions.append("<option value=\"/web/CtlTransaction?requestType=inqFruitToShipping\">Fruit to Shipping - Fresh Slice");
     headerInfo.addElement(setExtraOptions.toString());

   
//*****************************************************************************

%>
<html>
 <head>
   <title><%= inqTitle %></title>
   <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getCalendarHead() %>   
 </head>
 <body>
<%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
<%
  //*********************************************************************
   String formName = "inqSingleIngredientForward";
   String newRequestType = "inqSingleIngredientForward";
   String saveButtonDisplay = "GO";
   
   if (it.getRequestType().trim().equals("inqProductionDayBack") )
   {
      formName = "inqProductionDayBack";
      newRequestType = "inqProductionDayBack";
      saveButtonDisplay = "GO";
   }  
      if (it.getRequestType().trim().equals("inqProductionDayForward") )
   {
      formName = "inqProductionDayForward";
      newRequestType = "inqProductionDayForward";
      saveButtonDisplay = "GO";
   }  
         if (it.getRequestType().trim().equals("inqFruitToShipping") )
   {
      formName = "inqFruitToShipping";
      newRequestType = "inqFruitToShipping";
      saveButtonDisplay = "GO";
   }
   request.setAttribute("formName", formName);
%>
  <form  name = "<%= formName %>" action="/web/CtlTransaction?requestType=<%= newRequestType %>" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", it.getInqEnvironment()) %>

  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!it.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102">&nbsp;</td>
        <td class="td03200102" colspan = "4"><b><%= it.getDisplayMessage().trim() %></b></td>
        <td class="td03200102">&nbsp;</td>
       </tr>    
<%
   }
%>       
       <tr class="tr02">
        <td class="td0420" colspan = "2">
         <b> Choose:</b>
        </td>
        <td class="td0420" style="text-align:right" colspan = "2"><b>and then press</b>
         <%= HTMLHelpers.buttonSubmitRight("goButton", saveButtonDisplay ) %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>  
       </tr>
       
       <tr class="tr00"
	 <% if (it.getRequestType().equals("inqSingleIngredientForward")) { %>
			style="display:none;"
	<%	 } %>
	 >

        <td class="td04140102" style="width:2%">&nbsp;</td>
        
        <td class="td04140102" style="width:20%;">
         <acronym title="Facility"><b>Facility:</b></acronym>
        </td>
        
        <td class="td03140102">&nbsp;
         <%= InqTransaction.buildDropDownFacility(it.getInqEnvironment(), it.getInqFacility()) %>
        </td>
        
        <td class="td04140102" style="width:2%">&nbsp;</td>
       </tr> 
       
       
	 <tr class="tr00" 
	 <% if (it.getRequestType().equals("inqSingleIngredientForward")) { %>
			style="display:none;"
	<%	 } %>
	 >
        <td class="td04140102" style="width:2%">&nbsp;</td>
        
        <td class="td04140102">
         <acronym title="Start Date"><b>Start Date:</b></acronym>
        </td>
        
        <td class="td03140102">&nbsp;
         <%= HTMLHelpersInput.inputBoxDate("inqDate", it.getInqFromDate(), "cal1", "Y", "N") %>
        </td>
        
        <td class="td04140102" style="width:2%">&nbsp;</td>
       </tr> 
       
       <tr class="tr00">
        <td class="td04140102" style="width:2%">&nbsp;</td>
        
        <td class="td04140102">
         <acronym title="Must be a valid Number"><b>Item Number:</b></acronym>
        </td>
        
        <td class="td03140102">&nbsp;
         <%= HTMLHelpersInput.inputBoxNumber("inqItem", it.getInqItem(), "Item Number", 15, 15, "Y", "N") %>&nbsp;&nbsp;
         <b><%= it.getInqItemError() %></b>
        </td>
        
        <td class="td04140102" style="width:2%">&nbsp;</td>
       </tr> 
       
       <% if (it.getRequestType().equals("inqProductionDayBack") 
	 	 || it.getRequestType().equals("inqProductionDayForward") 
		 || it.getRequestType().equals("inqFruitToShipping")) {  %>
       <tr class="tr02">
        
        <td class="td0420" colspan="4" style="font-weight:bold;">Or:</td>
        
       </tr> 
       
       <tr class="tr00">
        <td class="td04140102" style="width:2%">&nbsp;</td>
        <td class="td04140102">
         <acronym title="Must be a valid Number"><b>Manufacturing Order Number:</b></acronym>
        </td>
        <td class="td03140102">&nbsp;
         <%= HTMLHelpersInput.inputBoxNumber("inqOrder", it.getInqOrder(), "Order Number", 15, 15, "Y", "N") %>&nbsp;&nbsp;
         <b><%= it.getInqOrderError() %></b>
        </td>
        <td class="td04140102" style="width:2%">&nbsp;</td>
       </tr> 
       
       <% } %>
        
	 <tr class="tr00" 
	 <% if (it.getRequestType().equals("inqProductionDayBack") 
	 	 || it.getRequestType().equals("inqProductionDayForward") 
		 || it.getRequestType().equals("inqFruitToShipping")) { %>
			style="display:none;"
	<%	 } %>
	 >
        <td class="td04140102" style="width:2%">&nbsp;</td>
        <td class="td04140102">
         <acronym title="Must be a Valid Lot Number"><b>Lot Number:</b></acronym>
        </td>
        <td class="td03140102">&nbsp;
         <%= HTMLHelpersInput.inputBoxNumber("inqLot", it.getInqLot(), "Lot Number", 20, 20, "Y", "N") %>&nbsp;&nbsp;
         <b><%= it.getInqLotError() %></b>
        </td>
        <td class="td04140102" style="width:2%">&nbsp;</td>
       </tr> 
       
       
          
     </table>  
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table>  
  </form>  
    <%= JavascriptInfo.getCalendarFoot(it.getRequestType(), "cal1", "inqDate") %> 
  <br>  

  <%= HTMLHelpers.pageFooterTable("") %> 
   </body>
</html>