<%@ page import="com.treetop.utilities.html.HTMLHelpers" %> 
<%@ page import="java.util.*" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.treetop.data.*" %>
<%@ page import="com.treetop.SessionVariables" %>
<%

//Heading Parameters (Included in the Heading .jsp)
 
//Parameters which are defined and set into the request attribute FROM the JSP, 
//to be included in the heading.  These parameter (if used) should be set in 
//the JSP BEFORE the heading include.

//•	title
//  o	if not set, default is “”;
//•	newAddWindow	 
//  o	default is to Not throw a new window.
//  o	if this equals “throwNew”, then it will throw a new page from the select an option section.
//•	hitResult
//  o	 if not set, default is “”, which means you can see the hits counted.
//•	parameterList
//  o	If null will send back to TreeNetInq if there is a problem.
//  o	EXAMPLE:
//      ?	“&returnToPage=/web/CtlSampleRequest”;
//•	extraOptions
//  o	Only need to use this if you want another option added to the select an option drop down list.
//  o	EXAMPLE: 
//      ?	"<option value=\"/web/CtlSampleRequest?requestType=add\">        Add a Sample Request"
//•	pageType
//  o	default is “” (see everything)  
//  o	logon = see nothing (not even TreeNet Image) 
//  o	secureSite = had hit counter and help only, and will see TreeNet Image
//•	addFolder ** Taken out of heading.jsp
//  o	do not currently use, when we put documents back on there this may come into effect.
//--------------------------- heading.jsp ----------------------------------//
// Will be included in all JSP's
//                                                                             
//   Date       Name          Comments                                           
//  ------      -----        ------------------------------------  
//   1/22/07    TWalton     Changed the look when put into NEW TreeNet
//                            Also remove the File and Folder Application Stuff
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Include/css/main.css" />
<link type="text/css" href="${pageContext.request.contextPath}/Include/css/cupertino/jquery-ui-1.8.22.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/Include/js/libs/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Include/js/libs/jquery-ui-1.8.22.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Include/js/script.min.js"></script>

 <script type="text/javascript">
<% String userID = SessionVariables.getSessionttiUserID(request, response); 
// if the user id is null or empty set the string accordingly
if(userID == null){
	userID="null";
}else if(userID.equals("")){
	userID="empty";
}
%>
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-27858316-1']);
_gaq.push(['_setCustomVar',
      1,             // This custom var is set to slot #1.  Required parameter.
      'User ID',   // The name of the custom variable.  Required parameter.
      '<%= userID %>',      // Sets the value of "User Type" to "Member" or "Visitor" depending on status.  Required parameter.
       2             // Sets the scope to session-level.  Optional parameter.
   ]);
  _gaq.push(['_setSiteSpeedSampleRate', 100]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<%
	String title = (String) request.getAttribute("title");
	if (title == null)
	   title = "";
	String newAddWindow = (String) request.getAttribute("newAddWindow");
	if (newAddWindow == null)
	   newAddWindow = "location.href=newPage1";
	if (newAddWindow.equals("throwNew"))
	   newAddWindow = "window.open(newPage1)";   
	String hitResult = (String) request.getAttribute("hitResult");
	if (hitResult == null)
	   hitResult = "";
   	String parameterListx = (String) request.getAttribute("parameterList");
   	if (parameterListx == null)
   	   parameterListx = "&returnToPage=/web/TreeNetInq";
   	String extraOptions = (String) request.getAttribute("extraOptions");
   	if (extraOptions == null)
   	   extraOptions = "";
   	String pageType    = (String) request.getAttribute("pageType");
   	if (pageType == null)
   	  pageType = "";
   	int    totalHits = 0;
%>
  <link rel="stylesheet" type="text/css" href="https://image.treetop.com/webapp/Stylesheetv2.css" />
 