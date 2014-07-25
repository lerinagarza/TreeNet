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
<html>
 <head>
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
  <script language="JavaScript1.2">
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
// (For use in drop down list of links)
function JumpToIt(list) {
    var newPage = list.options[list.selectedIndex].value
    if (newPage != "None") {
       location.href=newPage
   }
}
// (For use in drop down list of Options)
function JumpToIt1(list) {
    var newPage1 = list.options[list.selectedIndex].value
    if (newPage1 != "None") {
        <%= newAddWindow %>
   }
}
//  End -->
  </script>
  <link rel="stylesheet" type="text/css" href="https://image.treetop.com/webapp/Stylesheetv2.css" />
 </head>
<body>
 <table cellspacing="0" style="width:100%" background="https://image.treetop.com/webapp/headerTableBgV2.jpg">
  <tr>
    <td class="td04100905" style="width:125px" align="center" rowspan="2">
   <!-- 
    <img src="https://image.treetop.com/webapp/TTNewLogoSmallTransparent.gif">
     -->
     	<img src="/web/Include/images/TT_logo2C-2013.png" style="height:40px" >
     	</td>
   <td class="td04200405" style="text-align:center; vertical-align:middle">
    <b> 
<%
   if (!pageType.equals("logon"))
   {
%>  
     <img style="vertical-align:text-bottom" src="https://image.treetop.com/webapp/treenet.gif">        
<%
   }
%>            
      &nbsp;&nbsp;&nbsp;<%= title %>&nbsp;
    </b> 
   </td>
   <td class="td04101005" style="width:125px; text-align:center;" align="center" rowspan="2">
    <img src="https://image.treetop.com/webapp/newProducts.gif">&nbsp;
   </td>
  </tr>
  <tr class="tr00">
   <td class="td04100505">&nbsp;
    <table style="width:100%">
     <tr class="tr00">
      <td class="td0410" style="width:33%">
<%
   if (!pageType.equals("secureSite") &&
       !pageType.equals("logon"))
   {
%>                  
       <a class="a0410" href="/web/TreeNetInq">
        TreeNet Home
       </a>
<%
   }
%>                
       &nbsp;
      </td>
      <td class="td0410" style="text-align:center">
<%
   String xx = SessionVariables.getSessionttiProfile(request, response);

   if (hitResult.equals("")&&
       !pageType.equals("logon"))
   {
		// get authority object for this session
		String auth = request.getHeader("Authorization");
		
		// get Server
		String theHost = request.getHeader("Host");
		
		// get URI
		String theLongUrl = theHost + request.getRequestURI();
		int xxx = theLongUrl.indexOf("?");
		String theUrl = "";
		
		if (xxx == -1)
			theUrl = theLongUrl;
		else
			theUrl = theLongUrl.substring(0,xxx);
			
		// get current user profile
		String user = com.treetop.SessionVariables.getSessionttiProfile(request, response);
		
		// get current system date
		String[] dateAndTime = com.treetop.SystemDate.getSystemDate();

		// get hit counter information		
		java.sql.Date oneTimeDate = java.sql.Date.valueOf(dateAndTime[7]);
		String secSys = SessionVariables.getSessionSecuritySystem(request, response);
		if (secSys == null)
			secSys = "";
		String secVal = SessionVariables.getSessionSecurityValue(request, response);
		if (secVal == null)
			secVal = "";
		String secUsr = SessionVariables.getSessionSecurityUser(request, response);
		if (secUsr == null)
			secUsr = "";
		
		Counter.addHit(theUrl,user,oneTimeDate,
					   secSys, secVal, secUsr);
		
		totalHits = Counter.findSumOfHitsByUrl(theUrl);
		
  //                   <jsp:include page="/servlet/com.treetop.servlets.Counter" />
  		
        String hitsPage = theHost + "/web/JSP/countResults.jsp";
        hitsPage = hitsPage + "?theurl=" + theLongUrl;
        hitsPage = hitsPage + "&thedate=" + dateAndTime[7];
        hitsPage = hitsPage + "&theuser=" + user;
        String yy = SessionVariables.getSessionttiUserID(request, response);
        
        hitsPage = hitsPage + "&thecount=" + totalHits; 		

        out.println("<a class=\"a0410\" href=http://" +
            hitsPage + " target=\"_blank\"> Visited " + totalHits + " times </a> ");
   }
   else
   {
	   if (hitResult.equals("blank"))
       hitResult = "";
   }
%>
       &nbsp;
      </td>
      <td class="td0410" style="text-align:right; width:33%">
<%
   if (xx != null &&
       !xx.toLowerCase().equals("default") &&
       !pageType.equals("logon"))
   {
%>                  
       <a class="a0410" href="mailto:helpdesk@treetop.com?Subject=Application Home Menu">Help</a>
<%
   }
%>    
       &nbsp;&nbsp;                           
      </td>
     </tr>
     <tr class="tr00">
      <form action="/web/TreeNetList" method="get"> 
      <td class="td0410">
<%
   if (!pageType.equals("logon") &&
       !pageType.equals("secureSite"))
   {    
%>
       <input type="hidden" name="type" value="search">
       <input style="font-family:arial; font-size:7pt" size = "20" type="text" name="typevalue">
       <input style="font-family:arial; font-size:7pt" type="submit" value="Search">
<%
   }
%>         
      </td>
      </form> 
      <td style="text-align:center">
<%
   if (!pageType.equals("logon") &&
       !pageType.equals("secureSite"))
   {    
%>        
       <select style="font-family:arial; font-size:7pt" onChange="JumpToIt1(this)">
        <option value="None" selected>Select an Option
         <%= extraOptions %>
       </select>
<%
   }
%>    
       &nbsp;        
      </td>
      <td style="text-align:right">
<%
   if (!pageType.equals("logon") &&
       !pageType.equals("secureSite"))
   {   
      out.println(HTMLHelpers.dropDownHeadingLinks());
   }
%>    
       &nbsp;     
      </td>
     </tr>
    </table>
    &nbsp;
   </td>
  </tr>
 </table>
</body>
</html>
