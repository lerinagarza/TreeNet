<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- dtlSampleUsers.jsp ---------------------------------------//
//
//  Author :  David Eisenheim  6/20/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/18/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
//**************************************************************************//
  //************ This code has to be on every JSP (First Code)  ************//
  //************  for the headings and setting up variables  ***************//
   request.setAttribute("title", "Sample User Detail");
   String parameterList = "&returnToPage=CtlSampleUsers?requesttype=inquiry";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleUsers?requesttype=insert\">Add a New User");
 
   String updatePage = "CtlSampleUsers?requesttype=update";
   String deletePage = "CtlSampleUsers?requesttype=delete";
   String selectPage = "CtlSampleUsers?requesttype=inquiry";

   String expand1 = "";
   String expandimg1 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand2 = "";
   String expandimg2 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand3 = "";
   String expandimg3 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand4 = "";
   String expandimg4 = "https://image.treetop.com/webapp/minusbox3.gif";

   String requestType = (String) request.getAttribute("requesttype");

%>
<html>
<head>

<script language="JavaScript1.2">
<!--
 var head="display:'none'"
 function viewmenu(header)
 {
    var head=header.style
    if (head.display=="none")
    {
       head.display=""
    }
    else
    {
       head.display="none"
    }
 }
 var head1="display:'none'"
 function expand(header1)
 {
    var head1=header1.style
    if (head1.display=="none")
    {
       head1.display=""
    }
    else
    {
       head1.display="none"
    }
 }

 var imageURL = new Array(4);
 for (i = 0; i <= 4; i++)
 {
    imageURL[i] = "https://image.treetop.com/webapp/minusbox3.gif";
 }

 function changeImage(recordCount,imageCount)
 {
    i = recordCount;
    z = imageCount;

    if (imageURL[i]=="https://image.treetop.com/webapp/plusbox3.gif")
    {
       imageURL[i] = "https://image.treetop.com/webapp/minusbox3.gif";
    }
    else
    {
       imageURL[i] = "https://image.treetop.com/webapp/plusbox3.gif";
    }
    document.images[z].src = imageURL[i];
 }
//-->
</script>

<title>Detail Sample User Information</title>

</head>

<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>

<%
  if (requestType != null && requestType.equals("deletefinal"))
  {
     String nameType    = (String) request.getParameter("nametype");
     String nameProfile = (String) request.getParameter("nameprofile");
%>

  <table class="table01001" cellspacing="0">

      <tr>
         <td style="width:10%" rowspan="2"></td>
         <td class="td04140102" style="width:20%"><b>User Type:</b></td>
         <td class="td04140102"><%= nameType %></td>
         <td style="width:25%" rowspan="2"></td>
      </tr>

      <tr>
         <td class="td04140102"><b>Profile Name:</b></td>
         <td class="td04140102"><%= nameProfile %></td>
      </tr>

  </table>
<%
  }

  else
  {
//*************** List of parameters sent from the servlet *****************//
  try
  {
     SampleRequestUsers userDetail = (SampleRequestUsers) request.getAttribute("userdetail");
     java.sql.Date changeDate = userDetail.getUpdateDate();
     String[] datesArray = GetDate.getDates(changeDate);
     updatePage = updatePage + "&usertype=" + userDetail.getUserType().trim() + "&userprofile=" + userDetail.getUserProfile().trim();
     deletePage = deletePage + "&usertype=" + userDetail.getUserType().trim() + "&userprofile=" + userDetail.getUserProfile().trim();
%>
  <table class="table01" cellspacing="0">
      <tr>
         <td style="width:10%" rowspan="2">&nbsp;</td>
         <td class="td04140102" style="width:20%"><b>User Type:</b></td>
         <td class="td04140102"><%= userDetail.getUserType() %></td>
         <td style="width:10%" rowspan="2">&nbsp;</td>
         <td class="td04140102" style="width:20%"><b>Last Revised Date/Time:&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
         <td class="td04140102"><%= userDetail.getUpdateDate() %>&nbsp;&nbsp;&nbsp;<%= userDetail.getUpdateTime() %></td>
         <td style="width:10%">&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b>Profile Name:</b></td>
         <td class="td04140102"><%= userDetail.getUserProfile() %></td>
         <td class="td04140102"><b>Last Revised By:</b></td>
         <td class="td04140102"><%= userDetail.getUpdateUserName() %></td>
         <td class="td0412">
<%
      if (requestType == null || !requestType.equals("delete"))
      {
%>
               <div>
                         &nbsp;&nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
                       onClick="viewmenu(document.all[this.sourceIndex+1]);">
                  </div>
                      <span class="spanstyle" style="display:none" style=&{head}; >
                      &nbsp;<a href="<%= updatePage %>" target="_blank">Change User Info</a><br>
                    &nbsp;<a href="<%= deletePage %>" target="_blank">Delete User Info</a><br>
                        </span>
<%
      }
%>         
         </td>
  </table>
<%
//---  Show user detail information ----------------------------------------->
%>
  <table class="table00">
      <tr class="tr02">
          <td class="td03200102">
              <font style="color:#990000;
                         font-weight:bold;
                         font-family:arial;
                         font-size:12pt;
                         text-align:left;">
                   &nbsp;<img src="<%= expandimg2 %>" style="cursor:hand"
                          onClick="expand(document.all[this.sourceIndex+1]);
                          changeImage(1,3);">
                   Details
              </font>

  <span <%= expand2 %> style=&{head1};>

  <table class="table00" cellspacing="0" cellpadding="0">
      <tr class="tr00">
          <td style="width:5%" rowspan="3">&nbsp;</td>
          <td class="td04140102" style="width:25%"><b>Name Description:</b></td>
          <td class="td04140102"><%= userDetail.getUserName() %>&nbsp;</td>
          <td style="width:5%" rowspan="3">&nbsp;</td>
      </tr>
      <tr class="tr00">
          <td class="td04140102"><b>Name Initials</b></td>
          <td class="td04140102"><%= userDetail.getUserInitials() %>&nbsp;</td>
      </tr>

      <tr class="tr00">
          <td class="td04140102"><b>eMail (Shipped):</b></td>
          <td class="td04140102"><%= userDetail.getNotifyShipped() %></td>
      </tr>
  </table>
  </span>
           </td>
      </tr>
  </table>
<%

   if (requestType != null && requestType.equals("delete"))
   {
%>
      <form action="/web/CtlSampleUsers" method="post">
         <input type="hidden" name="requesttype" value="deletefinish">
         <input type="hidden" name="nametype" value="<%= userDetail.getUserType() %>">
         <input type="hidden" name="nameprofile" value="<%= userDetail.getUserProfile() %>">
       
    <table class="table01" cellspacing="0">
    <tr class="tr01">
         <td style="text-align:center">
            <input type="Submit" value="Verify Delete">
         </td>
      </tr>
   </table>
      </form>   
<%
   }
   }
   catch (Exception e)
   {
     String msg = "No User Information Available";
   }
   }
%>
<jsp:include page="../../Include/footer.jsp"></jsp:include>

</body>
</html>