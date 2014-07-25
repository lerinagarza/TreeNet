<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "javax.servlet.http.*" %>
<%
//---------------- listSample.jsp ---------------------------------------//
//
//  Author :  Tom Haile  7/07/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/18/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
   // Retrieve sessions variables to determine additional selection criteria.
   HttpSession sess = request.getSession(true);
   String salesAuth = (String) sess.getAttribute("salesAuth");
   if (salesAuth == null)
      salesAuth = "";
   String recvAuth  = (String) sess.getAttribute("recvAuth");
   if (recvAuth == null)
      recvAuth = "";

   String sendExtraOptions = "";
   if (salesAuth.equals("yes") || recvAuth.equals("yes") )
      sendExtraOptions = "<option value=\"/web/CtlSampleRequest\">Reselect" +
                         "<option value=\"/web/CtlSampleRequest?requestType=add\">Add a Sample Request";
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Sample Request List");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", sendExtraOptions);
//***********************************************************
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String template = request.getParameter("template");
   if (template == null)
      template = "";

   String version = request.getParameter("version");
   if (version == null)
      version = "";

//*** Use this variable to resend the Parameters for use with Order By
   String additionalParmResend = "template=" + template +
                               "&version="  + version;
//*************************************************
//  Order by can be
//        NumberA       = Sample Number Ascending (Default if Blank)
//        NumberD       = Sample Number Descending
//        StatusA       = Sample Status Ascending
//        StatusD       = Sample Status Descending
//        TypeA         = Type Ascending
//        TypeD         = Type Descending
//        ReceivedDateA = Received Date Ascending
//        ReceivedDateD = Received Date Descending
//        SalesRepA     = Sales Rep Ascending
//        SalesRepD     = Sales Rep Decending
//        ShippedDateA  = Shipped Date Ascending
//        ShippedDateD  = Shipped Date Decending
//        CustomerNameA = Customer Name Ascending
//        CustomerNameD = Customer Name Decending
//        DeliveryDateA = Delivery Date Ascending
//        DeliveryDateD = Delivery Date Decending
//*******************************************************************************
   String sortImg1 = "https://image.treetop.com/webapp/null.gif";
   String sortCol1 = "NumberA";
   String sortImg2 = "https://image.treetop.com/webapp/null.gif";
   String sortCol2 = "StatusA";
   String sortImg3 = "https://image.treetop.com/webapp/null.gif";
   String sortCol3 = "TypeA";
   String sortImg4 = "https://image.treetop.com/webapp/null.gif";
   String sortCol4 = "ReceivedDateA";
   String sortImg5 = "https://image.treetop.com/webapp/null.gif";
   String sortCol5 = "SalesRepA";
   String sortImg6 = "https://image.treetop.com/webapp/null.gif";
   String sortCol6 = "ShippedDateA";
   String sortImg7 = "https://image.treetop.com/webapp/null.gif";
   String sortCol7 = "CustomerNameA";
   String sortImg8 = "https://image.treetop.com/webapp/null.gif";
   String sortCol8 = "DeliveryDateA";
   String orderBy = request.getParameter("orderby");
   if (orderBy == null)
      orderBy = "NumberA";
   if (orderBy.equals("NumberA"))
   {
      sortImg1 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol1 = "NumberD";
   }
   if (orderBy.equals("NumberD"))
   {
      sortImg1 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("StatusA"))
   {
      sortImg2 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol2 = "StatusD";
   }
   if (orderBy.equals("StatusD"))
   {
      sortImg2 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("TypeA"))
   {
      sortImg3 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol3 = "TypeD";
   }
   if (orderBy.equals("TypeD"))
   {
      sortImg3 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("ReceivedDateA"))
   {
      sortImg4 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol4 = "ReceivedDateD";
   }
   if (orderBy.equals("ReceivedDateD"))
   {
      sortImg4 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("SalesRepA"))
   {
      sortImg5 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol5 = "SalesRepD";
   }
   if (orderBy.equals("SalesRepD"))
   {
      sortImg5 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("ShippedDateA"))
   {
      sortImg6 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol6 = "ShippedDateD";
   }
   if (orderBy.equals("ShippedDateD"))
   {
      sortImg6 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("CustomerNameA"))
   {
      sortImg7 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol7 = "CustomerNameD";
   }
   if (orderBy.equals("CustomerNameD"))
   {
      sortImg7 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

   if (orderBy.equals("DeliveryDateA"))
   {
      sortImg8 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
      sortCol8 = "DeliveryDateD";
   }
   if (orderBy.equals("DeliveryDateD"))
   {
      sortImg8 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
   }

//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

//*** Vector of Sample's
   int sampleCount = 0;
   Vector listSamples  = new Vector();
   Vector listStatus   = new Vector();
   Vector listType     = new Vector();
   Vector listSalesRep = new Vector();
   Vector listCustomer = new Vector();
   Vector listSecurity = new Vector();

try
{
   listSamples  = (Vector) request.getAttribute("listSamples");
   listStatus   = (Vector) request.getAttribute("listStatus");
   listType     = (Vector) request.getAttribute("listType");
   listSalesRep = (Vector) request.getAttribute("listSalesRep");
   listCustomer = (Vector) request.getAttribute("listCustomer");
   listSecurity = (Vector) request.getAttribute("listSecurity");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Samples Vector) = " + e);
}
try
{
   sampleCount = listSamples.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Sample's Vector) = " + e);
   sampleCount = 0;
}

   String printPage = "CtlSampleRequest?requestType=print";
%>
<html>
<head>
<style>
<!--
.spanstyle{
position:absolute;
z-index:100;
background-color:F3FAFF;
width:230px;
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
<script language="JavaScript">
   function confirmBox(deleteURL)
   {
     input_box = confirm("Click OK to Delete this transaction OR Cancel to return");

     if (input_box == true)
     {
        window.location = deleteURL;
     }
   }
</script>
<title>List from Inquiry Sample Request</title>
<script language="javascript">
var lists = new Array();

// First set of text and values
lists['01']    = new Array();
lists['01'][0] = new Array(
	'List'
);
lists['01'][1] = new Array(
	'01',
	'02'
);

// Second set of text and values
lists['02']    = new Array();
lists['02'][0] = new Array(
	'Printer Friendly'
);
lists['02'][1] = new Array(
	'02'
);
</script>

<%= JavascriptInfo.getDynamicDropDownScript("version") %>

</head>

<body onload="changeList(document.forms['listSampleRequest'].template)">
<jsp:include page="../../Include/heading.jsp"></jsp:include>

  <table class="table01" cellspacing="0">
     <form name="listSampleRequest" action="/web/CtlSampleRequest?requestType=list<%=generalInfo[1]%>" method="post">
    <tr class="tr01">
      <td style="width:3%" rowspan="2">&nbsp;</td>
      <td class="td0416">
        <b>Select a Report Format&nbsp;</b>
        <select name="template" size=1 onchange="changeList(this)">
          <option value="01">Summary
          <option value="02">Line Item Detail
        </select>
        <select name="version" size=1></select>
      </td>
      <td class="td0416" style="text-align:right">
        <b>Make Selections and then press</b>
        <input type="Submit" value="Go">
      </td>
       <td style="width:3%">&nbsp;</td>
    </tr>
        </form> 
   <tr>
       <td class="td0410">&nbsp;&nbsp;&nbsp;<%= generalInfo[0] %></td>
   </tr>
</table>

<table class="table00" cellspacing="0" cellpadding="1">
<%
//---  Header Section of Data List -------------->
%>
     <tr class="tr02">
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg1 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol1 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">

          Request Number</a>
       </td>
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg2 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol2 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Status</a>
       </td>
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg3 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol3 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Type</a>
       </td>
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg4 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol4 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Date Received</a>
       </td>
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg5 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol5 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Sales Rep</a>
       </td>
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg6 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol6 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Date Shipped</a>
       </td>
       <td class="td04120102" style="text-align:center">
          Location 
       </td>            
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg7 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol7 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Customer Name</a>
       </td>
       <td class="td04120102" style="text-align:center">
          <img src="<%= sortImg8 %>">
          <a class="a0412" href="/web/CtlSampleRequest?requestType=list&orderby=<%= sortCol8 %>&<%=additionalParmResend%><%= generalInfo[1] %> ">
          Delivery Date</a>
       </td>
       <td style="width:5%">&nbsp;</td>
     </tr>
<%
  for (int x = 0; x < sampleCount; x++)
   {
      SampleRequestOrder thisrow = (SampleRequestOrder) listSamples.elementAt(x);
      GeneralInfo thisType = (GeneralInfo) listType.elementAt(x);
      GeneralInfo thisStatus = (GeneralInfo) listStatus.elementAt(x);
      SampleRequestUsers thisSalesRep = (SampleRequestUsers) listSalesRep.elementAt(x);
      SampleRequestCustomer thisCustomer = (SampleRequestCustomer) listCustomer.elementAt(x);
      String allowAccess =  (String) listSecurity.elementAt(x);
%>
     <tr class="tr00">
       <td class="td04120102">
          <a class="a0412" style="text-align:center" href="/web/CtlSampleRequest?requestType=detail&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank"><%= thisrow.getSampleNumber() %></a>
       </td>
       <td class="td04120102"><%= thisStatus.getDescFull() %>&nbsp;
       </td>
       <td class="td04120102"><%= thisType.getDescFull() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getReceivedDate() %>&nbsp;
       </td>
       <td class="td04120102"><%= thisSalesRep.getUserName() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center">
<%
   if (thisrow.getStatus().trim().equals("SH") ||
       thisrow.getStatus().trim().equals("CO"))
   {
%>
          <%= thisrow.getStatus() %><%= thisrow.getShippingDate() %>
<%
   }
%>
          &nbsp;
       </td>
       <td class="td04120102">      
<%
		String displayInfo    = thisrow.getLocation().trim();
		
	 	try
		{
			GeneralInfo appClass = new GeneralInfo("LOC",
		                                            25,
		                                            thisrow.getLocation().trim(),
		                                        	"  ",
		                                        	"  ",
		                                        	"  ");
			displayInfo       = appClass.getDescFull().trim();	
		}
		catch(Exception e)
		{
		}
%>       
          <%= displayInfo %>&nbsp;
       </td>    
       <td class="td04120102"><%= thisCustomer.getName() %>&nbsp;
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getDeliveryDate() %>&nbsp;
       </td>

       <td class="td04120102">
          <div>
             &nbsp; &nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
             onClick="viewmenu(document.all[this.sourceIndex+1]);">
          </div>
          <span class="spanstyle" style="display:none" style=&{head1}; >
<%        if (allowAccess.equals("yes"))
          {
             String url = "CtlSampleRequest?" +
                          "requestType=delete&sampleNumber=" + thisrow.getSampleNumber() +
                          "&" + additionalParmResend + generalInfo[1];

%>
             &nbsp;<a href="/web/CtlSampleRequest?requestType=update&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank">Change Sample Request</a><br>
<%

             if (thisrow.getStatus().trim().equals("OP") ||
             thisrow.getStatus().trim().equals("PD"))
             {
%>
             &nbsp;<a href="JavaScript:confirmBox('<%= url %>')">Delete this Sample</a><br>
<%
             }
          }
          if (salesAuth.equals("yes") || recvAuth.equals("yes") )
          {
%>
             &nbsp;<a href="/web/CtlSampleRequest?requestType=copy&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank">Copy Sample Request</a><br>
<%
          }
%>
             &nbsp;<a href="/web/CtlSampleRequest?requestType=defineLabel&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank">Print Labels</a><br>
             &nbsp;<a href="<%= printPage %>&sampleNumber=<%=thisrow.getSampleNumber()%>" target="_blank" >Print Sample Request</a><br>
             &nbsp;<a href="<%= printPage %>remarks&sampleNumber=<%=thisrow.getSampleNumber()%>" target="_blank" >Print Sample Request with Remarks</a><br>
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