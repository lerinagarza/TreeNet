<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%
//---------------- updCustomer.jsp ---------------------------------------//
//
//  Author :  Teri Walton  7/2/03                                      
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
   request.setAttribute("title", "Update Customer");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleCustomer?requestType=add\">Add a New Sample Customer");

//**************************************************************************//
// Get Request Session Variables for Display on this Screen
//**************************************************************************
   String[] generalInfo           = (String[]) request.getAttribute("generalInfo");

   SampleRequestCustomer thisCust = new SampleRequestCustomer();
try
{
   thisCust = (SampleRequestCustomer) request.getAttribute("whichCust");
}
catch (Exception e)
{
   System.out.println("Problem when loading SampleRequestCustomer()");
}

   String requestType = (String) request.getAttribute("requestType");
   if (requestType == null)
      requestType = "add";

// Resetting the Request Type for what to do when leaving this page.
   String rdRadioValue = "";
   String prodRadioValue = "";
   String sendRequestType = "addcust";
   if (requestType.equals("add"))
   {
      rdRadioValue = "checked";
   }

   if (requestType.equals("copy") || requestType.equals("addcust"))
      sendRequestType = "addFinish";
   if (requestType.equals("update"))
      sendRequestType = "updateFinish";


//**************************************************************************

 // Default Information to come in open
   String expand1 = "";
   String expandimg1 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand2 = "";
   String expandimg2 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand3 = "";
   String expandimg3 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand4 = "";
   String expandimg4 = "https://image.treetop.com/webapp/minusbox3.gif";

%>
<html>
<head>

<script language="JavaScript1.2">
<!--
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

<title>Update/Add Sample Request Customer</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<form action="/web/CtlSampleCustomer" method="post">
   <input type="hidden" name="requestType" value="<%= sendRequestType %>">
   <table class="table00" cellspacing="0">
      <tr>
         <td style="width:5%" rowspan="3">&nbsp;</td>
         <td class="td04140102" style="width:20%"><b>Company:</b></td>
         <td class="td04140102">
            <%= generalInfo[1] %>&nbsp;
<%
   if (!requestType.equals("add"))
   {
%>
            <input type="hidden" name="company" value="<%= generalInfo[1] %>">
<%
   }
%>
         </td>
<%
   if (!requestType.equals("add") && !requestType.equals("copy") && !requestType.equals("addcust"))
   {
%>
         <td class="td04140102">
            <b>Last Revised Date/Time:</b>
         </td>
         <td class="td04140102">
            <%= thisCust.getUpdateDate() %>&nbsp;&nbsp;&nbsp;<%= thisCust.getUpdateTime() %>
         </td>
<%
   }
%>
         <td style="width:  5%" rowspan="3">&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b>Customer Number:</b></td>
         <td class="td04140102">
<%
   if (requestType.equals("add"))
   {
%>
            <input size="6" type="text" maxlength="6" name="custNumber" value="">
            <input type="Submit" value="GO">
         </td>
         <td class="td045CL001">
             &nbsp;You can Pre-Load the fields with information by inputing a M3 (Movex) Customer Number. <br>
             &nbsp;If you leave Customer Number blank or if the M3 (Movex) Customer Number does not exist, it will generate a brand new number.
<%
   }
   else
   {
%>
            <input type="hidden" name="custNumber" value="<%= thisCust.getCustNumber() %>">
            <%= thisCust.getCustNumber() %>&nbsp;
<%
   }
%>
         </td>
<%
   if (!requestType.equals("add") && !requestType.equals("copy") && !requestType.equals("addcust"))
   {
%>
         <td class="td04140102">
            <b>Last Revised By:</b>
         </td>
         <td class="td04140102">
            <%= thisCust.getUpdateUserLong() %>
         </td>
<%
   }
%>
      </tr>
<%
   if (!requestType.equals("add"))
   {
%>
      <tr>
         <td class="td04140102">
            <b>Customer Name:</b>
         </td>
         <td class="td04140102" colspan="2">
            <input size="40" type="text" maxlength="30" name="name" value="<%= thisCust.getName() %>">
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
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:4%" rowspan="13">&nbsp;</td>
                     <td class="td04140102" style="width:25%"><b>Address 1:</b></td>
                     <td class="td04140102">
                        <input size="60" type="text" maxlength="60" name="address1" value="<%= thisCust.getAddress1() %>">
                     </td>
                     <td style="width:4%" rowspan="13">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Address 2:</b></td>
                     <td class="td04140102">
                        <input size="60" type="text" maxlength="60" name="address2" value="<%= thisCust.getAddress2() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>City:</b></td>
                     <td class="td04140102">
                        <input size="50" type="text" maxlength="50" name="city" value="<%= thisCust.getCity() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>State:</b></td>
                     <td class="td04140102">
                        <%= generalInfo[0] %>&nbsp;
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Zip and Extension:</b></td>
                     <td class="td04140102">
                        <input size="5" type="text" maxlength="5" name="zip" value="<%= thisCust.getZip() %>">
                        &nbsp;
                        <input size="4" type="text" maxlength="4" name="zipExtention" value="<%= thisCust.getZipExtention() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Foreign Postal Code/Country:</b></td>
                     <td class="td04140102">
                        <input size="8" type="text" maxlength="8" name="foreignPostalCode" value="<%= thisCust.getForeignPostalCode() %>">
                         &nbsp;
                        <input size="50" type="text" maxlength="50" name="foreignCountry" value="<%= thisCust.getForeignCountry() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102" colspan = "2">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Customer Contact:</b></td>
                     <td class="td04140102">
                        <input size="30" type="text" maxlength="30" name="contactName" value="<%= thisCust.getContact() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Customer Contact Phone:</b></td>
                     <td class="td04140102">
                       <input size="30" type="text" maxlength="30" name="contactPhone" value="<%= thisCust.getContactPhone() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Customer Contact Email:</b></td>
                     <td class="td04140102">
                       <input size="60" type="text" maxlength="100" name="contactEmail" value="<%= thisCust.getContactEmail() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Main Shipping Company:</b></td>
                     <td class="td04140102">
                       <input size="30" type="text" maxlength="30" name="shippingCompany" value="<%= thisCust.getShippingCompany() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Shipping Account Number:</b></td>
                     <td class="td04140102">
                       <input size="30" type="text" maxlength="36" name="shippingAcct" value="<%= thisCust.getShippingAcct() %>">
                     </td>
                  </tr>
               </table>
            </span>
         </td>
      </tr>
     <tr class="tr01">
         <td class="td0416" colspan="3" style="text-align:center">
            <input type="Submit" value="Save Changes">
         </td>
      </tr>
<%
   }
%>

   </table>
      </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>