<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%
//---------------- dtlCustomer.jsp ---------------------------------------//
//
//  Author :  Teri Walton  7/01/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/16/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Sample Customer Detail");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleCustomer?requestType=add\">Add a New Sample Customer");

//*****************************************************************************

  // Default Information to come in open
   String expand1 = "";
   String expandimg1 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand2 = "";
   String expandimg2 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand3 = "";
   String expandimg3 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand4 = "";
   String expandimg4 = "https://image.treetop.com/webapp/minusbox3.gif";
//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

// Get in the Request Customer Class.
   SampleRequestCustomer thisCust = new SampleRequestCustomer();
try
{
   thisCust = (SampleRequestCustomer) request.getAttribute("customerDetail");
}
catch (Exception e)
{
   System.out.println("Problem when loading SampleRequestCustomer()");
}

   String requestType = (String) request.getAttribute("requestType");

%>
<html>
<head>
<style>
<!--
.spanstyle{
position:absolute;
z-index:100;
background-color:F3FAFF;
width:120px;
right:20;
}
-->
</style>
<script language="JavaScript1.2">
<!--
/************************************/
/*  Display the Menu Options        */
/************************************/
 var head1="display:'none'"
 function viewmenu(header1)
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
/************************************************************************************/
/* Define Variable and Create Function for Information within the Expanding Section */
/************************************************************************************/
 var head="display:'none'"
 function doit(header)
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

<title>Detail Sample Customer</title>
</head>
<body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>

   <table class="table01" cellspacing="0">
      <tr>
         <td style="width:10%" rowspan="3">&nbsp;</td>
         <td class="td04140102" style="width:25%"><b>Company:</b></td>
         <td class="td04140102"><%= thisCust.getCompany() %>&nbsp;</td>
         <td class="td0414" colspan="2">&nbsp;</td>
         <td style="width:10%" rowspan="2">
         </td>
      </tr>
      <tr>
         <td class="td04140102"><b>Sample Customer Number:</b></td>
         <td class="td04140102"><%= thisCust.getCustNumber() %>&nbsp;</td>
         <td class="td04140102"><b>Last Revised Date/Time:</b></td>
         <td class="td04140102"><%= thisCust.getUpdateDate() %>&nbsp;&nbsp;&nbsp;<%= thisCust.getUpdateTime() %></td>
      </tr>
      <tr>
         <td class="td04140102"><b>Sample Customer Name:</b></td>
         <td class="td04140102"><%= thisCust.getName() %>&nbsp;</td>
         <td class="td04140102"><b>Last Revised By:</b></td>
         <td class="td04140102"><%= thisCust.getUpdateUserLong() %>&nbsp;</td>
         <td class="td04140102" style="text-align:center">
<%
   if (requestType == null || !requestType.equals("delete"))
   {
%>
          <div>
             &nbsp; &nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
             onClick="viewmenu(document.all[this.sourceIndex+1]);">
          </div>
          <span class="spanstyle" style="display:none" style=&{head1}; >
             &nbsp;<a href="/web/CtlSampleCustomer?requestType=update&custNumber=<%= thisCust.getCustNumber() %>" target="_blank">Change Customer</a><br>
             &nbsp;<a href="/web/CtlSampleCustomer?requestType=copy&custNumber=<%= thisCust.getCustNumber() %>" target="_blank">Copy Customer</a><br>
             &nbsp;<a href="/web/CtlSampleCustomer?requestType=delete&custNumber=<%= thisCust.getCustNumber() %>">Delete Customer</a><br>
          </span>
<%
   }
   else
   {
%>
   &nbsp;
<%
   }
%>
         </td>
      </tr>
   </table>
   <table class="table00">
      <tr class="tr02">
         <td class="td03200102">
            <font style="color:#990000;
                         font-weight:bold;
                         font-family:arial;
                         font-size:12pt;
                         text-align:left;">
                   &nbsp;<img src="<%= expandimg2 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(1,3);">
                   Details
            </font>
            <span <%= expand2 %> style=&{head};>
              <table class="table00" cellspacing="0" cellpadding="0">
                 <tr class="tr00">
                    <td style="width:5%" rowspan="12">&nbsp;</td>
                    <td class="td04140102" style="width:30%"><b>Address 1:</b></td>
                    <td class="td04140102"><%= thisCust.getAddress1() %>&nbsp;</td>
                    <td style="width:5%" rowspan="12">&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Address 2:</b></td>
                    <td class="td04140102"><%= thisCust.getAddress2() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>City:</b></td>
                    <td class="td04140102"><%= thisCust.getCity() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>State:</b></td>
                    <td class="td04140102"><%= thisCust.getState() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Zip Code:</b></td>
                    <td class="td04140102"><%= thisCust.getZip() %>&nbsp;-&nbsp;<%= thisCust.getZipExtention() %> </td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Foreign Postal Code/Country:</b></td>
                    <td class="td04140102"><%= thisCust.getForeignPostalCode() %>&nbsp;&nbsp;<%= thisCust.getForeignCountry() %></td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Contact:</b></td>
                    <td class="td04140102"><%= thisCust.getContact() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Contact Phone:</b></td>
                    <td class="td04140102"><%= thisCust.getContactPhone() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Contact E-mail:</b></td>
                    <td class="td04140102"><%= thisCust.getContactEmail() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Shipping Company:</b></td>
                    <td class="td04140102"><%= thisCust.getShippingCompany() %>&nbsp;</td>
                 </tr>
                 <tr class="tr00">
                    <td class="td04140102"><b>Shipping Account:</b></td>
                    <td class="td04140102"><%= thisCust.getShippingAcct() %>&nbsp;</td>
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
      <form action="/web/CtlSampleCustomer" method="post">
        <input type="hidden" name="requestType" value="deleteFinish">
         <input type="hidden" name="custNumber" value="<%= thisCust.getCustNumber() %>">      
   <table class="table00" cellspacing="0">

     <tr class="tr01">
         <td class="td0414" style="text-align:center">
            <input type="Submit" value="Verify Delete">
         </td>
      </tr>
   </table>
      </form>   
<%
   }
%>
 <jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>