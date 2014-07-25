<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- listCustomer.jsp ---------------------------------------//
//
//  Author :  Teri Walton  6/30/03                                      
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
   request.setAttribute("title", "List of Selected Sample Customers's");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleCustomer?requestType=add\">Add a New Sample Customer");

//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String custNumber      = request.getParameter("custNumber");
       if (custNumber == null)
         custNumber = "";
   String fromCustNumber  = request.getParameter("fromCustNumber");
       if (fromCustNumber == null)
         fromCustNumber = "";
   String toCustNumber    = request.getParameter("toCustNumber");
       if (toCustNumber == null)
         toCustNumber = "";
   String name            = request.getParameter("name");
       if (name == null)
         name = "";
   String city            = request.getParameter("city");
       if (city == null)
         city = "";
   String state           = request.getParameter("state");
       if (state == null)
         state = "";
   String country         = request.getParameter("country");
       if (country == null)
         country = "";
   String contactName     = request.getParameter("contactName");
       if (contactName == null)
         contactName = "";

//*** Use this variable to resend the Parameters for use with Order By
   String parmResend = "&custNumber="     + custNumber +
                       "&fromCustNumber=" + fromCustNumber +
                       "&toCustNumber="   + toCustNumber +
                       "&name="           + name +
                       "&city="           + city +
                       "&state="          + state +
                       "&country="        + country +
                       "&contactName="    + contactName;

//*************************************************
//  Order by can be
//     NumberA      = Customer Number Ascending
//     NumberD      = Customer Number Descending
//     NameA        = Customer Name Ascending (Default if Blank)
//     NameD        = Customer Name Descending
//     CityA        = City Ascending
//     CityD        = City Descending
//     StateA       = State Ascending
//     StateD       = State Descending
//     ContactA     = Contact Name Ascending
//     ContactD     = Contact Name Descending
//*************************************************
   String sortImg1 = "https://image.treetop.com/webapp/null.gif";
   String sortCol1 = "NumberA";
   String sortImg2 = "https://image.treetop.com/webapp/null.gif";
   String sortCol2 = "NameA";
   String sortImg3 = "https://image.treetop.com/webapp/null.gif";
   String sortCol3 = "CityA";
   String sortImg4 = "https://image.treetop.com/webapp/null.gif";
   String sortCol4 = "StateA";
   String sortImg5 = "https://image.treetop.com/webapp/null.gif";
   String sortCol5 = "ContactA";
   String sortImg6 = "https://image.treetop.com/webapp/null.gif";
   String sortCol6 = "CountryA";
   String orderby = request.getParameter("orderby");
   if (orderby == null)
      orderby = "NameA";
    if (orderby.equals("NumberA"))
    {
       sortImg1 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol1 = "NumberD";
    }
    if (orderby.equals("NumberD"))
    {
       sortImg1 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
    if (orderby.equals("NameA"))
    {
       sortImg2 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol2 = "NameD";
    }
    if (orderby.equals("NameD"))
    {
       sortImg2 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
    if (orderby.equals("CityA"))
    {
       sortImg3 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol3 = "CityD";
    }
    if (orderby.equals("CityD"))
    {
       sortImg3 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
    if (orderby.equals("StateA"))
    {
       sortImg4 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol4 = "StateD";
    }
    if (orderby.equals("StateD"))
    {
       sortImg4 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
    if (orderby.equals("ContactA"))
    {
       sortImg5 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol5 = "ContactD";
    }
    if (orderby.equals("ContactD"))
    {
       sortImg5 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
    if (orderby.equals("CountryA"))
    {
       sortImg6 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol6 = "CountryD";
    }
    if (orderby.equals("CountryD"))
    {
       sortImg6 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }

//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

//*** Vector of Customer's
   int CustomerCount = 0;
   Vector ListCustomers = new Vector();

try
{
   ListCustomers = (Vector) request.getAttribute("listCustomers");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Customer Vector) = " + e);
}

try
{
   CustomerCount = ListCustomers.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Customer Vector) = " + e);
}

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
//  End -->
      </script>
<title>List from Inquiry Sample Request</title>

</head>

<body>
 
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<table class="table01001" cellspacing="0">
   <tr>
       <td style="width:2%">
       </td>
       <td class="td0510">Parms Queried: <%= generalInfo[0] %>
       </td>
   </tr>
</table>

<table class="table00" cellspacing="0" cellpadding="1">
     <tr class="tr02">
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg1 %>">
          <a class="a0414" href="/web/CtlSampleCustomer?requestType=list&orderby=<%= sortCol1 %><%= parmResend %> ">
          Customer Number</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg2 %>">
          <a class="a0414" href="/web/CtlSampleCustomer?requestType=list&orderby=<%= sortCol2 %><%= parmResend %> ">
          Customer Name</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg3 %>">
          <a class="a0414" href="/web/CtlSampleCustomer?requestType=list&orderby=<%= sortCol3 %><%= parmResend %> ">
          City</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg4 %>">
          <a class="a0414" href="/web/CtlSampleCustomer?requestType=list&orderby=<%= sortCol4 %><%= parmResend %> ">
          State</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg6 %>">
          <a class="a0414" href="/web/CtlSampleCustomer?requestType=list&orderby=<%= sortCol6 %><%= parmResend %> ">
          Country</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg5 %>">
          <a class="a0414" href="/web/CtlSampleCustomer?requestType=list&orderby=<%= sortCol5 %><%= parmResend %> ">
          Customer Contact</a>
       </td>
       <td style="width:5%">&nbsp;</td>
     </tr>
<%
  for (int x = 0; x < CustomerCount; x++)
   {
      SampleRequestCustomer thisrow = (SampleRequestCustomer) ListCustomers.elementAt(x);
%>
     <tr class="tr00">
       <td class="td04120102" style="text-align:center">
          <a class="a0412" href="/web/CtlSampleCustomer?requestType=detail&custNumber=<%= thisrow.getCustNumber() %>" target="_blank"><%= thisrow.getCustNumber() %></a>
       </td>
       <td class="td04120102"><%= thisrow.getName() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getCity() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getState() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getForeignCountry() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getContact() %>&nbsp;
       </td>
       <td class="td04120102">
          <div>
             &nbsp; &nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
             onClick="viewmenu(document.all[this.sourceIndex+1]);">
          </div>
          <span class="spanstyle" style="display:none" style=&{head1}; >
             &nbsp;<a href="/web/CtlSampleCustomer?requestType=update&custNumber=<%= thisrow.getCustNumber() %>" target="_blank">Change Customer</a><br>
             &nbsp;<a href="/web/CtlSampleCustomer?requestType=copy&custNumber=<%= thisrow.getCustNumber() %>" target="_blank">Copy Customer</a><br>
             &nbsp;<a href="/web/CtlSampleCustomer?requestType=delete&custNumber=<%= thisrow.getCustNumber() %>" target="_blank">Delete Customer</a><br>
          </span>
       </td>
     </tr>
<%
   }
%>
  </table>
<jsp:include page="../../Include/footer.jsp"></jsp:include>

</body>
</html>