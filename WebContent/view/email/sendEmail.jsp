<%@ page language = "java" %>
<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp"></jsp:include>
<%  String environment = (String) request.getParameter("environment");
    if (environment == null || environment.equals("")) { 
        environment="PRD";
    }
    
    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    if (roles != null && Arrays.asList(roles).contains("1")) {
        internal = true;
    }
%>
<%-- tpl:put name="headarea" --%>
	<title>Enter/Send Email</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.email.*" %>   
<%
//  Enter and Send an EMAIL --
//    Using TreeNet and Java
//------------ updEmail.jsp---------------------//
//  Author :  Teri Walton  12/02/05
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
// 20140324   Tom Haile   New header.
//------------------------------------------------------------//
 String errorPage = "/APP/Email/updEmail.jsp";
 String updateTitle  = "Enter/Send Email";
  // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 SendEmail se = new SendEmail();
 try
 {
	se = (SendEmail) request.getAttribute("sendViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }   
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",updateTitle);
//*****************************************************************************    
%>
 
<table class="table01001" cellspacing="0" >
 <tr>
  <td>&nbsp;</td> 
  <td>
   <table class="table01001" cellspacing="0" >
    <form name = "sendEmail" action="/web/CtlEmail" method="post">
    <tr class="tr00001">
     <td rowspan="6">&nbsp;</td>  
     <td class="td041CL002">
      <b>To:</b>
     </td>
     <td class="td041CL002">
<%
   if (!se.getEmailToError().equals(""))
   {
      out.println("<font color=\"#990000\">");
      out.println(se.getEmailToError());
      out.println("</font><br>");
   }   
%>     
        <%= HTMLHelpersInput.inputBoxText("emailTo", se.getEmailTo().trim(), "To Email", 60, 100, "Y", "N")%>
     </td>
     <td rowspan="6">&nbsp;</td>          
    </tr>  
    <tr>
     <td>
      <b>Fromm:</b>
     </td>
     <td>
<%
   if (!se.getEmailFromError().equals(""))
   {
      out.println("<font color=\"#990000\">");
      out.println(se.getEmailFromError());
      out.println("</font><br>");
   }   
%>          
        <%= HTMLHelpersInput.inputBoxText("emailFrom", se.getEmailFrom().trim(), "From Email", 60, 100, "Y", "N")%>
     </td>
    </tr>     
    <tr class="tr00001">
     <td class="td041CL002">
      <b>Subject:</b>
     </td>
     <td class="td041CL002">
        <%= HTMLHelpersInput.inputBoxText("emailSubject", se.getEmailSubject().trim(), "Subject of Email", 60, 100, "Y", "N")%>
     </td>
    </tr>     
    <tr class="tr00001">
     <td class="td041CL002" rowspan = "2">
      <b>Body:</b>
     </td>
     <td class="td041CL002">
        <%= HTMLHelpersInput.inputBoxTextarea("body", se.getBody().trim(), 4, 60, 500, "N")%>
     </td>
    </tr>     
    <tr class="tr00001">
     <td class="td041CL002">
        Set Email Information:<br>
        <%= HTMLHelpersInput.inputBoxTextarea("additionalBody", se.getAdditionalBody().trim(), 4, 60, 500, "Y")%>
     </td>
    </tr>  
    <tr class="tr00001">
     <td class="td041CC002" colspan = "2" class="center">
      <%= HTMLHelpers.buttonSubmit("saveButton", "Send Email", "") %>    
     </td>
    </tr>  
    
    </form> 
   </table>  
  </td>
  <td>&nbsp;</td> 
 </tr>  
</table>
  

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>