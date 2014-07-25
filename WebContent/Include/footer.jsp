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
<html>
<body>
 <table cellspacing="0" style="width:100%">
  <tr class="tr02">
   <td class="td0410" style="text-align:center">
    Confidential Property of Tree Top Inc.
   </td>      
   <td class="td0410" style="text-align:center">
    <%  String[] dateArrayx = com.treetop.SystemDate.getSystemDate(); %>
    <%= dateArrayx[6] %>, <%= dateArrayx[9] %>
   </td>
  </tr>
 </table>
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

</body>
</html>