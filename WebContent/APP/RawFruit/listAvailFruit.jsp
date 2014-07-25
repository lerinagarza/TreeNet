<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------  listAvailFruit.jsp  ------------------------------------------//
// -- List page for the Available Fruit Search
//    Author :  Teri Walton  9/8/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "/RawFruit/listAvailFruit.jsp";
  String listTitle = "List Available Fruit";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqAvailableFruit inqAvailFruit = new InqAvailableFruit();
 String inqRequestType = "inqAvailFruit";
 try
 {
	inqAvailFruit = (InqAvailableFruit) request.getAttribute("inqViewBean");
	if (inqAvailFruit.getRequestType().trim().equals("listAvailFruitAll"))
	   inqRequestType = "inqAvailFruitAll";
    else
       listTitle = listTitle + " for Scheduling";	   
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Part of the Page
    Vector headerInfo = new Vector();
    headerInfo.addElement(inqAvailFruit.getEnvironment());
    headerInfo.addElement(listTitle); // Element 0 is always the Title of the Page
    
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=" + inqRequestType + "&environment=" + inqAvailFruit.getEnvironment().trim() + "\">Select Again");
   // comment the if statement for now, will need to add this to security
//   if (inqFormula.getSecurityLevel().trim().equals("2"))
//   {
//      setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=addFormula&environment=" + inqFormula.getEnvironment() + "\">Add a NEW Formula");
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
  <form  name = "listAvail" action="/web/CtlRawFruit?requestType=schedAvailFruit" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", inqAvailFruit.getEnvironment().trim()) %> 
  <%= HTMLHelpersInput.inputBoxHidden("inqRegion", inqAvailFruit.getInqRegion().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqWhseNo", inqAvailFruit.getInqWhseNo().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqLocAddNo", inqAvailFruit.getInqLocAddNo().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqCrop", inqAvailFruit.getInqCrop().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqVariety", inqAvailFruit.getInqVariety().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqGrade", inqAvailFruit.getInqGrade().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqOrganic", inqAvailFruit.getInqOrganic().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqStickerFree", inqAvailFruit.getInqStickerFree().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqFruitAvailToPurchase", inqAvailFruit.getInqFruitAvailToPurchase().trim()) %>
<%
   if (!inqAvailFruit.getDisplayMessage().trim().equals(""))
   {
%>     
    <table class="table00" cellspacing="0" style="width:100%"> 
     <tr class="tr00">
      <td class="td03200102" colspan = "5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= inqAvailFruit.getDisplayMessage().trim() %></b></td>
     </tr>    
    </table>
<%
   }
%>   
  <table class="table00" cellspacing="0">
    <tr>
      <td style="width:2%">&nbsp;</td>
      <td class="td0410">
        <%= JavascriptInfo.getExpandingSection("C", "Selection Criteria", 8, 1, 2, 1, 0) %>
          <table class="table01" cellspacing="0">        
            <tr>
              <td class="td0410">
                <%= inqAvailFruit.buildParameterDisplay() %>
              </td> 
            </tr>
          </table>
         <%= HTMLHelpers.endSpan() %>       
      </td>
      <td class="td0414" style="text-align:right">
<%    
   if (inqAvailFruit.getRequestType().trim().equals("listAvailFruit")) 
     out.println(HTMLHelpers.buttonSubmit("goButton", "Go To Schedule Loads"));
%>      
      </td>
      <td style="width:2%">&nbsp;</td>  
    </tr>
  </table>
  <jsp:include page="listAvailFruitTable.jsp"></jsp:include>
  </form>
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