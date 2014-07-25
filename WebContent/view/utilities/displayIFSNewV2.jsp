<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------  displayIFSNew.jsp  -----------------------------//
//
//   Author :  Teri Walton  10/20/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//   9/25/06    twalton    Changed to look at NEW X Drive
//   4/07/08    twalton	   Changed to the NEW look (blue and grey)
//------------------------------------------------------------------//
//********************************************************************
%>

<div class="center">
<%
	String displayImagePath = (String) request.getAttribute("displayImagePath");
	if (displayImagePath == null) {
		displayImagePath = "https://image.treetop.com/webapp/";
	}
    
    String protocol = "";
 	if (displayImagePath.contains(":\\")) {
 		//convert back slashes to forward slashes
 		try {
 		displayImagePath = displayImagePath.replaceAll("\\\\+","/");
 		} catch (Exception e) {
 			System.out.println(e);
 		}
 		//add the file protocol
 		protocol = "file:///";
 	}
%>

	<a href="<%=protocol %><%=displayImagePath%>" 
		alt="This will take you to the folder structure">
		Click Here to go directly to <%=displayImagePath%>
	</a>
	<br />
	<br />
	<div class="ui-comment">
	Please note that if this folder is not created you will receive a <br />
	<strong>"The page cannot be displayed"</strong> display.
	</div>


</div>
