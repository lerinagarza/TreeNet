<%//--------------------footer.jsp---------------------------------------------
//  Author :  Teri Walton  04/28/03
//Changes:
//     Use on Every Page, to create a uniform footer across every application.
//  Date       Name       Comments
//  ----       ----       --------
//  2/25/07  twalton      Changed the look when put into NEW TreeNet
//  5/9/05   twalton	  Add a confidential property of TreeTop Section.	
//-----------------------------------------------------------------------------
%>

<div id="footer">
&copy; 2011. Confidential Property of Tree Top, Inc.
</div>

<%
	String stdcmt = "";
    String newcmt = "";
    try {
           stdcmt = (String) request.getAttribute("StandardComment");
           newcmt = (String) request.getAttribute("NewComment");
    }
    catch (Exception e) {
           out.println("Exception = " + e);
    }

	if (stdcmt == null)
		stdcmt = "";
		
   if (!stdcmt.equals(""))
   {
%>
      <script type="text/javascript">
      alert("<%= stdcmt %>" + '\n' + '\n' + "<%= newcmt %>");
      </script>
<%
   }
	//****  for to check for messages ***//
   String msgx = request.getParameter("msg");
   
   if (msgx == null || msgx.trim().equals("null") || msgx.trim().equals(""))
   {
      msgx = (String) request.getAttribute("msg");
      if (msgx == null || msgx.trim().equals("null") || msgx.trim().equals(""))
        msgx = "";
   }   
	request.setAttribute("msg", msgx);
	
   if (!msgx.equals(""))
   {
%>
      <script type="text/javascript">
      alert("<%= msgx %>")
      </script>
<%
   }
%>