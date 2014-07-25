<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------  listSchedFruit.jsp  ------------------------------------------//
// -- List page for the Scheduled Loads of Fruit Search
//    Author :  Teri Walton  9/24/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "/RawFruit/listSchedFruit.jsp";
  String listTitle = "List Scheduled Loads of Fruit";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqScheduledFruit inqSchedFruit = new InqScheduledFruit();
 try
 {
	inqSchedFruit = (InqScheduledFruit) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Part of the Page
    Vector headerInfo = new Vector();
    headerInfo.addElement(inqSchedFruit.getEnvironment());
    headerInfo.addElement(listTitle); // Element 0 is always the Title of the Page
    
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqSchedFruit&environment=" + inqSchedFruit.getEnvironment().trim() + "\">Select Again");
   // comment the if statement for now, will need to add this to security
//   if (inqFormula.getSecurityLevel().trim().equals("2"))
//   {
      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqAvailFruit&environment=" + inqSchedFruit.getEnvironment() + "\">Search for Available Fruit");
      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=addSchedFruitLoad&environment=" + inqSchedFruit.getEnvironment() + "&originalRequestType=inqSchedFruit" +  "\">Add a NEW Scheduled Load");   
      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=schedTransfer&environment=" + inqSchedFruit.getEnvironment() + "&originalRequestType=inqSchedFruit" +  "\">Add Transfer Loads");     
//   }
   headerInfo.addElement(setExtraOptions.toString());
					                              
//*****************************************************************************
%>
<html>
  <head>
    <title><%= listTitle %></title>
    <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
    <%= JavascriptInfo.getExpandingSectionHead("", 0, "Y", 1) %>
  </head>
  <body>
<%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
<%
  try
  {
%>  
  <table class="table00" cellspacing="0">
<%
  if (!inqSchedFruit.getInqOrderBySupplier().trim().equals(""))
  {
%>  
   <tr>
      <td style="width:2%">&nbsp;</td>
      <td class="td0418"><b>*** ORDER BY SUPPLIER ***</b></td>
      <td style="width:2%">&nbsp;</td>  
    </tr>
<%
   }
%>    
    <tr>
      <td style="width:2%">&nbsp;</td>
      <td class="td0410">
        <%= JavascriptInfo.getExpandingSection("C", "Selection Criteria", 8, 1, 2, 1, 0) %>
          <table class="table01" cellspacing="0">        
            <tr>
              <td class="td0410">
                <%= inqSchedFruit.buildParameterDisplay() %>
              </td> 
            </tr>
          </table>
         <%= HTMLHelpers.endSpan() %>       
      </td>
      <td style="width:2%">&nbsp;</td>  
    </tr>
  </table>
  <jsp:include page="listSchedFruitTable.jsp"></jsp:include>
<%
  }
  catch(Exception e)
  {
    System.out.println(JSPExceptionMessages.PageExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.PageExceptMsg(errorPage));
  }
%>
  <%= HTMLHelpers.pageFooterTable("") %>
   </body>
</html>