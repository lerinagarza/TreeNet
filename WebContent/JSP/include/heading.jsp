 
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
//•	addFolder 
//  o	do not currently use, when we put documents back on there this may come into effect.
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
      <link rel="stylesheet" type="text/css" href="https://image.treetop.com/webapp/Stylesheet.css" />

</head>
<body>
<%
  String addFolder = "";
  try
  {
     addFolder = (String) request.getAttribute("addFolder");
     if (addFolder == null)
        addFolder = "N";
  }
  catch (Exception e)
  {
     System.out.println("Exception Problem in the JSPa = " + e);
  }
   
%>
   
  <table class="table03001" cellspacing="0">
    <tr>
      <td rowspan="3" align="center" style="width:125px">
        <img src="https://image.treetop.com/webapp/TTLogoSmall.gif">	
      </td>
      <td class="td04SC002" colspan="3">
<%
   if (!pageType.equals("logon"))
   {
%>         
        <img style="vertical-align:text-bottom" src="https://image.treetop.com/webapp/treenet.gif">
<%
   }
%>            
        <%= title %>&nbsp;
      </td>
      <td rowspan="3" align="center" style="width:125px">
        <img src="https://image.treetop.com/webapp/TTJuiceBottle2.gif">	
      </td>
    </tr>
    <tr class="tr03001">
      <td class="td00001" colspan="3">
        <table>
          <tr class="tr00001">
            <td class="td048CL001" style="width:33%">
<%
   if (!pageType.equals("secureSite") &&
       !pageType.equals("logon"))
   {
%>                  
              <a class="a04001" href="/web/TreeNetInq">
                TreeNet Home
              </a>
<%
   }
%>                
              &nbsp;
            </td>
            <td class="td048CC001" style="width:33%">
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

        out.println("<a class=\"a04001\" href=http://" +
            hitsPage + " target=\"_blank\"> Visited " + totalHits + " times </a> <br>");
   }
   else
   {
	   if (hitResult.equals("blank"))
       hitResult = "";
   }
%>
					
            </td>
            <td class="td048CR001" style="width:33%">
              &nbsp;
<%
   if (xx != null &&
       !xx.toLowerCase().equals("default") &&
       !pageType.equals("logon"))
   {
%>                  
                     <a class="a04001" href="mailto:helpdesk@treetop.com?Subject=Application Home Menu">Help</a>
<%
   }
%>                              
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="tr00001">
        <form action="/web/TreeNetList" method="get"> 
      <td>
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
      
      <td>
<%
   if (!pageType.equals("logon") &&
       !pageType.equals("secureSite"))
   {    
%>        
        <select style="font-family:arial; font-size:7pt" onChange="JumpToIt1(this)">
          <option value="None" selected>Select an Option
<%
     String doNotShow = "Y";
     if (doNotShow.equals("N"))
     {
       if (addFolder.equals("Y"))
       {
%>
               <option value="/web/TreeNetUpd?request=add&type=folder<%= parameterListx %>">Create Folder
<%
       }
%>
               <option value="/web/TreeNetUpd?request=add&type=file<%= parameterListx %>">Add File
               <option value="/web/TreeNetUpd?request=add&type=link<%= parameterListx %>">Add Link
<%
     }
%>               
          <%= extraOptions %>
        </select>
<%
   }
%>            
      </td>
      <td class="td014CR001">
<%
   if (!pageType.equals("logon") &&
       !pageType.equals("secureSite"))
   {   
     //  <option value="http://www.fruitrocketz.com">Fruit Rocketz Site 
%>        
        <select style="font-family:arial; font-size:7pt" onChange="JumpToIt(this)">
          <option value="None" selected>Select a link
          <option value="http://www.treetop.org">Grower Site
          <option value="http://intranet.treetop.com">Tree Top Intranet
          <option value="http://www.treetop.com">Tree Top Site
          <option value="/web/JSP/DataWarehouse/inqDataWarehouse.jsp">Data Warehouse Site
          <option value="http://marketeer.treetop.com">Marketeer
          <option value="http://www.nwnaturals.com">Northwest Naturals
        </select>
<%
   }
%>        
      </td>
    </tr>
  </table>
</body>
</html>
