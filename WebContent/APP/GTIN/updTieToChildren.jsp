<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.data.*" %>
<%

//---------------- updTieToChildren.jsp -----------------------//
//   Author :  Teri Walton  9/22/2005
//   Date       Name       Comments
// --------   ---------   ------------------------------------
// 20140205   WTH         Modified add in dtlGTINFamilyTree.jsp//
//------------------------------------------------------------//
  String errorPage = "/GTIN/updTieToChildren.jsp";
   String updateTitle = "Update Parent to Children Relationship";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdTieToChildren updTieToChildren = new UpdTieToChildren();
 try
 {
	updTieToChildren = (UpdTieToChildren) request.getAttribute("updViewBean");
//	if (updTieToChildren != null &&
//	    updGTIN.getGtinNumber() != null &&
//	    !updGTIN.getGtinNumber().equals(""))
//	    updateTitle = updGTIN.getGtinNumber() + " : " + updateTitle;
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
 					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());         
   
//*****************************************************************************   
%>

<html>
 <head>
   <title><%= updateTitle %></title>
   <%= JavascriptInfo.getEditButton() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getEditButton() %>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 1, "Y", 1) %>   
    
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include> 
<table class="table01" cellspacing="0" style="width:100%">
 <tr>
  <td class="td04140105">&nbsp;</td> 
<%
//   Add this section if you want to select a new one ON this page.
%>   
  <td class="td04140105">
   <form method="link" action="#">
    <input type="submit" value="Select New GTIN"
        onClick="ANY_NAME=window.open('/web/JSP/Resource/GTIN/selGTIN.jsp?requestType=update&location=/web/CtlGTIN', 'win1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width=300,height=300'); return false;">
  </td>
   <%= HTMLHelpersInput.endForm() %>
  <td style="width:3%">
   &nbsp;
  </td>          
 </tr>
</table>
<table class="table00" cellspacing="0" cellpadding="3">
    <form name = "updTieToChildren" action="/web/CtlGTIN?" method="post">
    <%= HTMLHelpersInput.inputBoxHidden("requestType", updTieToChildren.getRequestType()) %>
 <tr>
  <td style="width:2%">&nbsp;</td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr01">
     <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102" colspan="2"><b>
	   Parent GTIN Number:&nbsp;&nbsp; 
	    <%= HTMLHelpersInput.inputBoxHidden("parentGTIN", updTieToChildren.getParentGTIN()) %>
	    <%= HTMLHelpersLinks.routerGTIN(updTieToChildren.getParentGTIN(), "a04002", "", "") %>	
	   </b>
	 </td>
	 <td class="td04140102" colspan="2">&nbsp;
<%
   if (updTieToChildren.getParentInfo() != null &&
       updTieToChildren.getParentInfo().getGtinLongDescription() != null)
       out.println(updTieToChildren.getParentInfo().getGtinLongDescription());
%>	 
	 </td>	 
	 <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getParentInfo() != null &&
       updTieToChildren.getParentInfo().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getParentInfo().getTradeItemUnitDescriptor());
%>	 
	 </td>	 	 
	 <td class="td04140102" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></td>
	</tr>
    <tr class="tr02">
	 <td class="td04140102" colspan="7"><b>
	   Children GTIN Numbers -<font style="font-size:10"> GTIN Numbers which DIRECTLY relate to the Parent <%= HTMLHelpersLinks.routerGTIN(updTieToChildren.getParentGTIN(), "", "", "") %></font>
	   </b>
	 </td>
	</tr>
    <tr class="tr01">
     <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102" style="text-align:center">
	  <b>
	   <acronym title = "Refers to the sequence in which the list of GTIN's will be displayed in the Family Tree.">
	   Seq #
	   </acronym> 
	  </b>
	 </td>
	 <td class="td04140102" style="text-align:center"><b>Child GTIN Number</b></td>
	 <td class="td04140102" style="text-align:center"><b>Qty in Parent</b></td>
     <td class="td04140102" style="text-align:center"><b>Description</b></td>
     <td class="td04140102"><b><acronym title="Trade Item Unit Descriptor">TIUD</acronym></b></td>     	 
     <td class="td04140102">&nbsp;</td>
	</tr>
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN1Sequence", updTieToChildren.getChildGTIN1Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN1A", updTieToChildren.getChildGTIN1A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN1B", updTieToChildren.getChildGTIN1B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN1C", updTieToChildren.getChildGTIN1C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN1D", updTieToChildren.getChildGTIN1D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN1QtyInParent", updTieToChildren.getChildGTIN1QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild1Info() != null &&
       updTieToChildren.getChild1Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild1Info().getGtinLongDescription());
%>     
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild1Info() != null &&
       updTieToChildren.getChild1Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild1Info().getTradeItemUnitDescriptor());
%>     
     </td>    
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  String reqType = "update";
  if (updTieToChildren.getChild1Info() == null ||
      updTieToChildren.getChild1Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN1().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN1(), "") %>
<%
  }
%>     
     </td>           
	</tr>
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN2Sequence", updTieToChildren.getChildGTIN2Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN2A", updTieToChildren.getChildGTIN2A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN2B", updTieToChildren.getChildGTIN2B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN2C", updTieToChildren.getChildGTIN2C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN2D", updTieToChildren.getChildGTIN2D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN2QtyInParent", updTieToChildren.getChildGTIN2QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild2Info() != null &&
       updTieToChildren.getChild2Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild2Info().getGtinLongDescription());
%>         
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild2Info() != null &&
       updTieToChildren.getChild2Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild2Info().getTradeItemUnitDescriptor());
%>         
     </td>     
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  reqType = "update";
  if (updTieToChildren.getChild2Info() == null ||
      updTieToChildren.getChild2Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN2().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN2(), "") %>
<%
  }
%>          
     </td>                
	</tr>
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN3Sequence", updTieToChildren.getChildGTIN3Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN3A", updTieToChildren.getChildGTIN3A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN3B", updTieToChildren.getChildGTIN3B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN3C", updTieToChildren.getChildGTIN3C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN3D", updTieToChildren.getChildGTIN3D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN3QtyInParent", updTieToChildren.getChildGTIN3QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild3Info() != null &&
       updTieToChildren.getChild3Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild3Info().getGtinLongDescription());
%>         
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild3Info() != null &&
       updTieToChildren.getChild3Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild3Info().getTradeItemUnitDescriptor());
%>         
     </td>     
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  reqType = "update";
  if (updTieToChildren.getChild3Info() == null ||
      updTieToChildren.getChild3Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN3().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN3(), "") %>
<%
  }
%>     
     </td>                
	</tr>
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN4Sequence", updTieToChildren.getChildGTIN4Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN4A", updTieToChildren.getChildGTIN4A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN4B", updTieToChildren.getChildGTIN4B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN4C", updTieToChildren.getChildGTIN4C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN4D", updTieToChildren.getChildGTIN4D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN4QtyInParent", updTieToChildren.getChildGTIN4QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild4Info() != null &&
       updTieToChildren.getChild4Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild4Info().getGtinLongDescription());
%>         
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild4Info() != null &&
       updTieToChildren.getChild4Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild4Info().getTradeItemUnitDescriptor());
%>         
     </td>     
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  reqType = "update";
  if (updTieToChildren.getChild4Info() == null ||
      updTieToChildren.getChild4Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN4().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN4(), "") %>
<%
  }
%>     
     </td>            
	</tr>		
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN5Sequence", updTieToChildren.getChildGTIN5Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN5A", updTieToChildren.getChildGTIN5A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN5B", updTieToChildren.getChildGTIN5B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN5C", updTieToChildren.getChildGTIN5C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN5D", updTieToChildren.getChildGTIN5D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN5QtyInParent", updTieToChildren.getChildGTIN5QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild5Info() != null &&
       updTieToChildren.getChild5Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild5Info().getGtinLongDescription());
%>         
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild5Info() != null &&
       updTieToChildren.getChild5Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild5Info().getTradeItemUnitDescriptor());
%>         
     </td>    
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  reqType = "update";
  if (updTieToChildren.getChild5Info() == null ||
      updTieToChildren.getChild5Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN5().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN5(), "") %>
<%
  }
%>     
     </td>              
	</tr>		
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN6Sequence", updTieToChildren.getChildGTIN6Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN6A", updTieToChildren.getChildGTIN6A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN6B", updTieToChildren.getChildGTIN6B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN6C", updTieToChildren.getChildGTIN6C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN6D", updTieToChildren.getChildGTIN6D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN6QtyInParent", updTieToChildren.getChildGTIN6QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild6Info() != null &&
       updTieToChildren.getChild6Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild6Info().getGtinLongDescription());
%>         
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild6Info() != null &&
       updTieToChildren.getChild6Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild6Info().getTradeItemUnitDescriptor());
%>         
     </td>     
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  reqType = "update";
  if (updTieToChildren.getChild6Info() == null ||
      updTieToChildren.getChild6Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN6().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN6(), "") %>
<%
  }
%>     
     </td>           
	</tr>	
    <tr>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN7Sequence", updTieToChildren.getChildGTIN7Sequence(), "", 5, 5, "N", "") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxText("childGTIN7A", updTieToChildren.getChildGTIN7A(), "", 2, 2, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN7B", updTieToChildren.getChildGTIN7B(), "", 6, 6, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN7C", updTieToChildren.getChildGTIN7C(), "", 5, 5, "N", "") %>&nbsp;
	   <%= HTMLHelpersInput.inputBoxText("childGTIN7D", updTieToChildren.getChildGTIN7D(), "", 1, 1, "N", "Y") %>
	 </td>
	 <td class="td04140102">
	   <%= HTMLHelpersInput.inputBoxNumber("childGTIN7QtyInParent", updTieToChildren.getChildGTIN7QtyInParent(), "", 5, 5, "N", "") %>
	 </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild7Info() != null &&
       updTieToChildren.getChild7Info().getGtinLongDescription() != null)
       out.println(updTieToChildren.getChild7Info().getGtinLongDescription());
%>         
     </td>
     <td class="td04140102">&nbsp;
<%
   if (updTieToChildren.getChild7Info() != null &&
       updTieToChildren.getChild7Info().getTradeItemUnitDescriptor() != null)
       out.println(updTieToChildren.getChild7Info().getTradeItemUnitDescriptor());
%>         
     </td>     
     <td class="td04140102" style="text-align:right">&nbsp;
<%
  reqType = "update";
  if (updTieToChildren.getChild7Info() == null ||
      updTieToChildren.getChild7Info().getGtinLongDescription() == null)   
     reqType = "add";
  if (!updTieToChildren.getChildGTIN7().trim().equals(""))   
  {
%>     
     <%= UpdTieToChildren.buildMoreButton(reqType, updTieToChildren.getChildGTIN7(), "") %>
<%
  }
%>     
     </td>           
	</tr>											
   </table>
  </td>       
  <td style="width:2%">&nbsp;</td>
 </tr>    
<%= HTMLHelpersInput.endForm() %>
<%
  //------GTIN Family Tree------
     request.setAttribute("reqType", "update");
     request.setAttribute("gtin", updTieToChildren.getParentGTIN());
%> 
 <tr>
 <td style="width:2%">&nbsp;</td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr02">
     <td class="td03141405">
      <%= JavascriptInfo.getExpandingSection("O", "GTIN Family Tree", 0, 1, 3, 1, 0) %><div style="text-align:right">
      <%@ include file="dtlGTINFamilyTree.jsp" %>   
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>
   </table>
  </td>
  <td style="width:2%">&nbsp;</td>
 </tr>   
</table> 
  <%@ include file="../../Include/footer.jsp" %>
   </body>
</html>