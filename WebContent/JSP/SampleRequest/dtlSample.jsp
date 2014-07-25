<%@page import="com.treetop.utilities.html.JavascriptInfo"%>
<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "javax.servlet.http.*" %>
<%
//---------------- dtlSample.jsp ---------------------------------------//
//
//  Author :  Teri Walton  7/14/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
// 11/19/03    Twalton     #6811 - Add copy Request Option to Edit Button
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  1/12/07    TWalton	   Take out the Word Chem -- just leave Additive
//  6/19/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//----------------------------------------------------------------------//

// Retrieve sessions variables to determine additional selection criteria.
   HttpSession sess = request.getSession(true);
   String salesAuth = (String) sess.getAttribute("salesAuth");
   if (salesAuth == null)
      salesAuth = "";
   String recvAuth  = (String) sess.getAttribute("recvAuth");
   if (recvAuth == null)
      recvAuth = "";

   String printPage = "CtlSampleRequest?requestType=print";
//*************************************************************************//
  String sendExtraOptions = "";
   if (salesAuth.equals("yes") || recvAuth.equals("yes") )
      sendExtraOptions = "<option value=\"CtlSampleRequest?requestType=add\">Add a Sample Request";
  
  //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Sample Request Detail");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", sendExtraOptions);
  
//**************************************************************************//
  // Default Information to come in open
   String expand1 = "";
   String expandimg1 = "https://image.treetop.com/webapp/plusbox3.gif";
   String expand2 = "";
   String expandimg2 = "https://image.treetop.com/webapp/plusbox3.gif";
   String expand3 = "";
   String expandimg3 = "https://image.treetop.com/webapp/plusbox3.gif";
   String expand4 = "";
   String expandimg4 = "https://image.treetop.com/webapp/plusbox3.gif";
//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

//*** Vector of Sample Request
   Vector sampleDetail = new Vector();
   int    sampleCount  = 0;
try
{
   sampleDetail = (Vector) request.getAttribute("sampleDetail");
}
catch (Exception e)
{
   System.out.println("dtlSample.jsp: Exception Problem in the JSP SampleNumber:(" + request.getParameter("sampleNumber") + ") (When receiving in the Sample Request Vector) = " + e);
}

try
{
   sampleCount = sampleDetail.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Sample Request Vector) = " + e);
}
   Vector listSecurity = new Vector();
try
{
   listSecurity = (Vector) request.getAttribute("listSecurity");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Sample Security Vector) = " + e);
}
   String allowAccess =  (String) listSecurity.elementAt(0);
//******************* Class Customer Info  ********************************
// IF there is not customer Make all the fields Blank
// If there is a customer, put the correct information in each field.
//
   SampleRequestCustomer sampleCust = new SampleRequestCustomer();
   String custNumber  = "";
   String custName    = "";
   String custAdd1    = "";
   String custAdd2    = "";
   String custCity    = "";
   String custState   = "";
   String custZip     = "";
   String custZipExt  = "";
   String custPostCd  = "";
   String custCountry = "";
try
{
   sampleCust = (SampleRequestCustomer) request.getAttribute("custInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Customer Information Vector) = " + e);
}
   if (sampleCust.getName() != null &&
       sampleCust.getCustNumber() != 0)
   {
      custNumber    = "" + sampleCust.getCustNumber();
      custName      = sampleCust.getName();
      custAdd1      = sampleCust.getAddress1();
      custAdd2      = sampleCust.getAddress2();
      custCity      = sampleCust.getCity();
      custState     = sampleCust.getState();
      if (sampleCust.getZip() != 0)
         custZip    = "" + sampleCust.getZip();
      if (sampleCust.getZipExtention() != 0)
         custZipExt = "-" + sampleCust.getZipExtention();
      custPostCd    = sampleCust.getForeignPostalCode();
      custCountry   = sampleCust.getForeignCountry();
   }
//*************************************************************************************************************
//*** Class - Sales Rep
   String salesRep = "";
   SampleRequestUsers sampleRep = new SampleRequestUsers();
try
{
   sampleRep = (SampleRequestUsers) request.getAttribute("repInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Sales Rep User Class) = " + e);
}
   if(sampleRep.getUserName() != null)
      salesRep = sampleRep.getUserName();

//*************************************************************************************************************
//*** Class - Person Receiving Sample Request
   String recPerson = "";
   SampleRequestUsers sampleRec = new SampleRequestUsers();
try
{
   sampleRec = (SampleRequestUsers) request.getAttribute("recInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Person Receiving Sample Request User Class) = " + e);
}
   if(sampleRec.getUserName() != null)
      recPerson = sampleRec.getUserName();

//*************************************************************************************************************
//*** Class - Status
   String status = "";
   GeneralInfo statusInfo = new GeneralInfo();
try
{
   statusInfo = (GeneralInfo) request.getAttribute("statusInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Status Class) = " + e);
}
   if(statusInfo.getDescFull() != null)
      status = statusInfo.getDescFull();

//*************************************************************************************************************
//*** Class - Sample Request Type
   String type = "";
   GeneralInfo typeInfo = new GeneralInfo();
try
{
   typeInfo = (GeneralInfo) request.getAttribute("typeInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Sample Request Type Class) = " + e);
}
   if(typeInfo.getDescFull() != null)
      type = typeInfo.getDescFull();

//*************************************************************************************************************
//*** Class - Sample Request Application
//	** Hide - requested Removal 9/29/05 TW Project 7142

//   String sampleApp = "";
//   GeneralInfo appInfo = new GeneralInfo();
//try
//{
//   appInfo = (GeneralInfo) request.getAttribute("appInfo");
//}
//catch (Exception e)
//{
//   System.out.println("Exception Problem in the JSP (When receiving in the Sample Request Application Class) = " + e);
//}
//   if(appInfo.getDescFull() != null)
//      sampleApp = appInfo.getDescFull();

//*************************************************************************************************************
//*** Class - Sample Request Ship Via
   String shipVia = "";
   GeneralInfo shipViaInfo = new GeneralInfo();
try
{
   shipViaInfo = (GeneralInfo) request.getAttribute("shipViaInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Ship Via Class) = " + e);
}
   if(shipViaInfo.getDescFull() != null)
      shipVia = shipViaInfo.getDescFull();

//*************************************************************************************************************
//*** Class - Sample Request Ship How
   String shipHow = "";
   GeneralInfo shipHowInfo = new GeneralInfo();
try
{
   shipHowInfo = (GeneralInfo) request.getAttribute("shipHowInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Ship How Class) = " + e);
}
   if(shipHowInfo.getDescFull() != null)
      shipHow = shipHowInfo.getDescFull();

//*************************************************************************************************************
//*** Vector - Detail Line, Product Group
   Vector prodGroup = new Vector();
try
{
   prodGroup = (Vector) request.getAttribute("prodGroup");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Product Group Vector) = " + e);
}
//*************************************************************************************************************
//*** Vector - Detail Line, Product Type
   Vector prodType = new Vector();
try
{
   prodType = (Vector) request.getAttribute("prodType");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Product Type Vector) = " + e);
}
//*************************************************************************************************************
//*** Vector - Detail Line, Cut Size
   Vector cutSize = new Vector();
try
{
   cutSize = (Vector) request.getAttribute("cutSize");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Cut Size Vector) = " + e);
}
//*************************************************************************************************************
//*** Vector - Detail Line, Color
   Vector color = new Vector();
   try
   {
      color = (Vector) request.getAttribute("color");
   }
   catch (Exception e)
   {
      System.out.println("Exception Problem in the JSP (When receiving in the color ) = " + e);
   }
//*************************************************************************************************************
//*** Vector - Detail Line, Flavor
   Vector flavor = new Vector();
   try
   {
      flavor = (Vector) request.getAttribute("flavor");
   }
   catch (Exception e)
   {
      System.out.println("Exception Problem in the JSP (When receiving in the flavor ) = " + e);
   }
//*************************************************************************************************************

//*** Vector - Detail Line, Chemical Additive
   Vector chemAdd = new Vector();
try
{
   chemAdd = (Vector) request.getAttribute("chemAdd");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Chemical Additive Vector) = " + e);
}
//*************************************************************************************************************
//*** Vector - Detail Line, Fruit Variety
   Vector fruitVar = new Vector();
try
{
   fruitVar = (Vector) request.getAttribute("fruitVar");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Fruit Variety Vector) = " + e);
}
//*************************************************************************************************************
//*** Vector - Detail Line, Fruit Type
   Vector fruitType = new Vector();
try
{
   fruitType = (Vector) request.getAttribute("fruitType");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Fruit Type Vector) = " + e);
}
//*************************************************************************************************************
//*** Vector - Detail Line, Shipped Fruit Variety
   Vector shippedFruitVar = new Vector();
try
{
   shippedFruitVar = (Vector) request.getAttribute("shippedFruitVar");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Shipped Fruit Variety Vector) = " + e);
}
//*************************************************************************************************************

%>
<html>
<head>
<%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>   
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
 var imageURL = new Array(4);
 for (i = 0; i <= 4; i++)
 {
    imageURL[i] = "https://image.treetop.com/webapp/plusbox3.gif";
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

<title>Detail Sample Request</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
if (sampleCount > 0)
{
      SampleRequestOrder thisrow = (SampleRequestOrder) sampleDetail.elementAt(0);
   if (thisrow.getSampleNumber() == null)
   {
%>
 <table class="table01" cellspacing="0">
   <tr>
     <td class="td0416" style="text-align:center">
      <b>
        The Sample Number you have chosen :<%= request.getParameter("sampleNumber") %>: is not valid.<br>
        And there is no information relating to that Sample Number.<br>
        Please try again.
      </b>
     </td>
   </tr>
 </table>   
<%   
   }else{   
%>

   <table class="table01" cellspacing="0">
      <tr>
         <td style="width:5%" rowspan="5">&nbsp;</td>
         <td class="td04140102" style="width:25%"><b>Sample Request Number:</b></td>
         <td class="td04140102" style="width:20%">
            <%= thisrow.getSampleNumber() %>&nbsp;
         </td>
         <td class="td04140102" style="width:20%"><b>Sample Status:</b></td>
         <td class="td04140102" style="width:25%">
            <%= status %>&nbsp;
         </td>
         <td style="width:5%" rowspan="4">&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b>Sample Type:</b></td>
         <td class="td04140102"><%= type %>&nbsp;</td>
         <td class="td04140102"><b>Sales Representative:</b></td>
         <td class="td04140102"><%= salesRep %>&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b><%//Application Type:%>&nbsp;</b></td>
         <td class="td04140102"><%// sampleApp %>&nbsp;</td>
         <td class="td04140102"><b>Sample Requested By:</b></td>
         <td class="td04140102"><%= thisrow.getWhoRequested() %>&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b>Sample Received Date and Time:</b></td>
         <td class="td04140102">
            <%= thisrow.getReceivedDate() %>&nbsp;<%= thisrow.getReceivedTime() %>&nbsp;
         </td>
         <td class="td04140102"><b>Sample Request To:</b></td>
         <td class="td04140102"><%= recPerson %>&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b>Sample Delivery Date:</b></td>
         <td class="td04140102"><%= thisrow.getDeliveryDate() %>&nbsp;</td>
         <td class="td04140102"><b>Broker Territory:</b></td>
         <td class="td04140102"><%= thisrow.getTerritory()%>&nbsp;</td>
<%         
//     Do not need the Broker Router for the New System -- YET
//			<a class="a04002" 
//			   href="/web/JSP/Router/brokerRouter.jsp?requestType=detail&brokerNumber= thisrow.getTerritory() "
//			    target="_blank"> thisrow.getTerritory() 
//			</a>
%>		
         <td class="td04140102" style="text-align: right">
             <div>
               &nbsp;&nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
               onClick="viewmenu(document.all[this.sourceIndex+1]);">
             </div>
             <span class="spanstyle" style="display:none" style=&{head1}; >
<%        
          if (allowAccess.equals("yes"))
          {
%>
             &nbsp;<a href="/web/CtlSampleRequest?requestType=update&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank">Change Sample Request</a><br>
<%
         }
         if (salesAuth.equals("yes") || recvAuth.equals("yes") )
         {
%>
             &nbsp;<a href="/web/CtlSampleRequest?requestType=copy&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank">Copy Sample Request</a><br>
<%
         }
%>
             &nbsp;<a href="/web/CtlSampleRequest?requestType=defineLabel&sampleNumber=<%= thisrow.getSampleNumber() %>" target="_blank">Print Labels</a><br>
                                       &nbsp;<a href="<%= printPage %>&sampleNumber=<%=thisrow.getSampleNumber()%>" >Print Sample Request</a><br>
                                       &nbsp;<a href="<%= printPage %>remarks&sampleNumber=<%=thisrow.getSampleNumber()%>" >Print Sample Request with Remarks</a><br>
                                      </span>
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
                   &nbsp;<img src="https://image.treetop.com/webapp/plusbox3.gif" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(1,3);">
                   Shipping Information
            </font>
            <span style="display:none" style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:4%" rowspan="20">&nbsp;</td>
                     <td class="td04140102"><b>Customer Number:</b></td>
                     <td class="td04140102"><%= thisrow.getCustNumber() %>&nbsp;</td>
                     <td class="td04140102"><b>Customer Name:</b></td>
                     <td class="td04140102"><%= custName %>&nbsp;</td>
                     <td style="width:4%" rowspan="20">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Customer Contact:</b></td>
                     <td class="td04140102"><%= thisrow.getCustContact() %>&nbsp;</td>
                     <td class="td04140102"><b>Customer Address 1:</b></td>
                     <td class="td04140102"><%= custAdd1 %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Customer Contact Phone:</b></td>
                     <td class="td04140102"><%= thisrow.getCustContactPhone() %>&nbsp;</td>
                     <td class="td04140102"><b>Customer Address 2:</b></td>
                     <td class="td04140102"><%= custAdd2 %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Customer Contact Email:</b></td>
                     <td class="td04140102"><%= thisrow.getCustContactEmail() %>&nbsp;</td>
                     <td class="td04140102"><b>Customer City and State:</b></td>
                     <td class="td04140102"><%= custCity %>&nbsp;&nbsp;<%= custState %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Mark To (Attn) Contact:</b></td>
                     <td class="td04140102"><%= thisrow.getAttnContact() %>&nbsp;</td>
                     <td class="td04140102"><b>Customer Zip Code and Extension:</b></td>
                     <td class="td04140102"><%= custZip %><%= custZipExt %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Mark To (Attn) Contact Phone:</b></td>
                     <td class="td04140102"><%= thisrow.getAttnContactPhone() %>&nbsp;</td>
                     <td class="td04140102"><b>Customer Postal Code and Country:</b></td>
                     <td class="td04140102"><%= custPostCd %>&nbsp;&nbsp;&nbsp;<%= custCountry %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Mark To (Attn) Contact Email:</b></td>
                     <td class="td04140102"><%= thisrow.getAttnContactEmail() %>&nbsp;</td>
                     <td class="td04140102"><b>Shipping Charge:</b></td>
                     <td class="td04140102"><%= thisrow.getShippingCharge() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102">&nbsp;</td>
                     <td class="td04140102">&nbsp;</td>
                     <td class="td04140102"><b>Ship Via:</b></td>
                     <td class="td04140102"><%= shipVia %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>G/L Account Number:</b></td>
                     <td class="td04140102"><%= thisrow.getGlAccountNumber() %>&nbsp;</td>
                     <td class="td04140102"><b>Ship How:</b></td>
                     <td class="td04140102"><%= shipHow %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>G/L Account Miscellaneous:</b></td>
                     <td class="td04140102"><%= thisrow.getGlAccountMisc() %>&nbsp;</td>
                     <td class="td04140102"><b>Tracking Number:</b></td>
                     <td class="td04140102"><%= thisrow.getTrackingNumber() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Shipping Location:</b></td>
                     <td class="td04140102">
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
          <%= displayInfo %>
                     
                     </td>
                     <td class="td04140102"><b>Shipping Date:</b></td>
                     <td class="td04140102"><%= thisrow.getShippingDate() %>&nbsp;</td>
                  </tr>
<%
//	** Hide - requested Removal 9/29/05 TW Project 7142
//   Integer[] seqNumDocument = thisrow.getCheckBoxSeqNumber();
//   int documentNumber = thisrow.getCheckBoxSeqNumber().length;
//   String first = "Yes";
//   for (int x = 0; x < documentNumber; x++)
//   {
//      String tdClass = "td041CL001";
//      if (x == (documentNumber - 1))
//         tdClass = "td041CL002";
   //HIDE
   if (0 == 1)
   {
%>
                  <tr class="tr00">
<%
//      if (seqNumDocument[x] != null)
//      {
//         if (first.equals("Yes"))
//         {
//            first = "no";
%>
                     <td class=<%// tdClass %>>Documents to send when Shipped:</td>
                     <td class="td04140102" colspan = "3"><%// thisrow.getCheckBoxValue20()[x] %>&nbsp;</td>
<%
//         }
//         else
//         {
%>
                     <td class=<%// tdClass %>>&nbsp;</td>
                     <td class="td04140102" colspan="3"><%// thisrow.getCheckBoxValue20()[x] %>&nbsp;</td>
<%
//         }
//      }
%>
                  </tr>
<%
   }
//   if (documentNumber == 0)
//   {
%>
                  <tr class="tr00">
                     <td class="td04140102"><b>Documents to send when Shipped:</b></td>
                     <td class="td04140102" colspan = "3">&nbsp;</td>
                  </tr>
<%
//   }
%>
                  <tr class="tr00">
                     <td class="td04140102">Send to when Shipped (E-Mail):</td>
                     <td class="td04140102" colspan = "3"><%= thisrow.getEmailWhenShipped1() %>&nbsp;</td>
                  </tr>
<%
      if (!thisrow.getEmailWhenShipped2().trim().equals(""))
      {
%>
                  <tr class="tr00">
                     <td class="td04140102"></td>
                     <td class="td04140102" colspan = "3"><%= thisrow.getEmailWhenShipped2().trim() %></td>
                  </tr>
<%
      }
%>
<%
      if (!thisrow.getEmailWhenShipped3().trim().equals(""))
      {
%>
                  <tr class="tr00">
                     <td class="td04140102"></td>
                     <td class="td04140102" colspan = "3"><%= thisrow.getEmailWhenShipped3().trim() %></td>
                  </tr>
<%
      }
%>
<%
      if (!thisrow.getEmailWhenShipped4().trim().equals(""))
      {
%>
                  <tr class="tr00">
                     <td class="td04140102"></td>
                     <td class="td04140102" colspan = "3"><%= thisrow.getEmailWhenShipped4().trim() %></td>
                  </tr>
<%
      }
%>
<%
      if (!thisrow.getEmailWhenShipped5().trim().equals(""))
      {
%>
                  <tr class="tr00">
                     <td class="td04140102"></td>
                     <td class="td04140102" colspan = "3"><%= thisrow.getEmailWhenShipped5().trim() %></td>
                  </tr>
<%
      }
%>
               </table>
            </span>
         </td>
      </tr>
      <tr class="tr02">
         <td class="td03200102">
            <font style="color:#990000;
                         font-weight:bold;
                         font-family:arial;
                         font-size:12pt;
                         text-align:left;">
                   &nbsp;<img src="https://image.treetop.com/webapp/plusbox3.gif" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(2,4);">
                   Line Items
            </font>
            <span style="display:none" style=&{head};>
               <table class="table00" cellspacing="0">
<%
   Integer[] seqNum   = thisrow.getDtlSeqNumber();
   int recordNumber = thisrow.getDtlSeqNumber().length;
   for (int x = 0; x < recordNumber; x++)
   {
      if (seqNum[x] != null)
      {
          GeneralInfoDried thisProdGroup = (GeneralInfoDried) prodGroup.elementAt(x);
          String productGroup = "";
          if (thisProdGroup.getDescFull() != null &&
              !thisProdGroup.getDescFull().equals("null"))
             productGroup = thisProdGroup.getDescFull();

          GeneralInfoDried thisProdType  = (GeneralInfoDried) prodType.elementAt(x);
          String productType = "";
          if (thisProdType.getDescFull() != null &&
              !thisProdType.getDescFull().equals("null"))
             productType = thisProdType.getDescFull();

          GeneralInfoDried thisCutSize  = (GeneralInfoDried) cutSize.elementAt(x);
          String productCutSize = "";
          if (thisCutSize.getDescFull() != null &&
              !thisCutSize.getDescFull().equals("null"))
             productCutSize = thisCutSize.getDescFull();

          GeneralInfoDried thisColor = (GeneralInfoDried) color.elementAt(x);
          String productColor = "";
          if (thisColor.getDescFull() != null &&
              !thisColor.getDescFull().equals("null"))
              productColor = thisColor.getDescFull();

          GeneralInfoDried thisFlavor = (GeneralInfoDried) flavor.elementAt(x);
          String productFlavor = "";
          if (thisFlavor.getDescFull() != null &&
              !thisFlavor.getDescFull().equals("null"))
              productFlavor = thisFlavor.getDescFull();

          GeneralInfoDried thisChemAdd  = (GeneralInfoDried) chemAdd.elementAt(x);
          String productChemAdd = "";
          if (thisChemAdd.getDescFull() != null &&
              !thisChemAdd.getDescFull().equals("null"))
             productChemAdd = thisChemAdd.getDescFull();

          GeneralInfoDried thisFruitType  = (GeneralInfoDried) fruitType.elementAt(x);
          String productFruitType = "";
          if (thisFruitType.getDescFull() != null &&
              !thisFruitType.getDescFull().equals("null"))
             productFruitType = thisFruitType.getDescFull();

          GeneralInfoDried thisFruitVariety  = (GeneralInfoDried) fruitVar.elementAt(x);
          String productFruitVariety = "";
          if (thisFruitVariety.getDescFull() != null &&
              !thisFruitVariety.getDescFull().equals("null"))
             productFruitVariety = thisFruitVariety.getDescFull();

          GeneralInfoDried thisShippedFruitVariety  = (GeneralInfoDried) shippedFruitVar.elementAt(x);
          String productShippedFruitVariety = "";
          if (thisShippedFruitVariety.getDescFull() != null &&
              !thisShippedFruitVariety.getDescFull().equals("null"))
             productShippedFruitVariety = thisShippedFruitVariety.getDescFull();
%>
                  <tr class="tr01">
                     <td class="td04120502">Quantity</td>
                     <td class="td04120302">Sample Size</td>
                     <td class="td04120302">Product Group</td>
                     <td class="td04120302">Fruit Type</td>
                     <td class="td04120302">Product Type</td>
                     <td class="td04120302">Product Size</td>
                     <td class="td04120302">Additive</td>
                     <td class="td04120302">Color</td>
                     <td class="td04120302">Flavor</td>
                     <td class="td04120302">Resource</td>
                     <td class="td04120302">Spec</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04121102" style="text-align:right">
                       <b><%= thisrow.getQuantity()[x] %></b>&nbsp;</td>
                     <td class="td04121302" style="text-align:right">
                       <b><%= thisrow.getContainerSize()[x] %>&nbsp;
                       <%= thisrow.getUnitOfMeasure()[x] %></b></td>
                     <td class="td04121302">
                       <b><%= productGroup %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= productFruitType %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= productType %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= productCutSize %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= productChemAdd %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= productColor %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= productFlavor %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= thisrow.getResource()[x] %></b>&nbsp;</td>
                     <td class="td04121302">
                       <b><%= thisrow.getSpecNumber()[x] %></b>&nbsp;</td>
                  </tr>
                   <tr class="tr00">
                     <td class="td04121202">&nbsp;</td>
                     <td class="td04121302" colspan="5">
                        <b><%= thisrow.getItemDescription()[x] %>&nbsp;&nbsp;
                        <%= thisrow.getAdditionalDescription()[x] %>
              
                       </b>      
                     </td>
                     <td class="td0412102401">Fruit Variety</td>
                     <td class="td0412102401">Shipped Fruit Variety</td>
                     <td class="td0412102401">Brix Level</td>
                     <td class="td0412102401">Lot Number</td>
                     <td class="td0412102401">Formula</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120524">&nbsp;</td>
                     <td class="td04120325" colspan="5">
 <%
   if (thisrow.getFormulaNumber()[x].intValue() > 0)
   {
%>    
      <b>Formula Number:</b>&nbsp;&nbsp;&nbsp; 
      <a class="a0412" href="/web/CtlRandDFormula?requestType=detail&formulaNumber=<%= thisrow.getFormulaNumber()[x] %>" target="_blank"><%= thisrow.getFormulaNumber()[x] %></a>
<%
   }
%>                                                    
                       &nbsp;</td>
                     <td class="td04120325">
                       <b><%= productFruitVariety %></b>&nbsp;</td>
                     <td class="td04120325">
                       <b><%= productShippedFruitVariety %></b>&nbsp;</td>
                     <td class="td04120325">
                       <b><%= thisrow.getBrixLevel()[x] %></b>&nbsp;</td>
                     <td class="td04120325">
                       <b><%= thisrow.getLotNumber()[x] %></b>&nbsp;</td>
                     <td class="td04120325">
                       <b><%= thisrow.getFormulaNumber()[x] %></b>&nbsp;</td>
                  </tr>
<%
       }
   }
%>
               </table>
            </span>
         </td>
      </tr>
      <tr class="tr02">
         <td class="td03200102">
            <font style="color:#990000;
                         font-weight:bold;
                         font-family:arial;
                         font-size:12pt;
                         text-align:left;">
                   &nbsp;<img src="https://image.treetop.com/webapp/plusbox3.gif" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(3,5);">
                   Comments/Remarks
            </font>
            <span style="display:none" style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr01">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0516">Comments:</td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%
   Integer[] seqNumComment = thisrow.getCommentSeqNumber();
   int commentNumber = thisrow.getCommentSeqNumber().length;
   for (int x = 0; x < commentNumber; x++)
   {
      if (seqNumComment[x] != null)
      {
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04140102"><b><%= thisrow.getComment()[x] %></b>&nbsp;</td>
                  </tr>
<%
       }
   }
%>
                  <tr class="tr01">
                     <td>&nbsp;</td>
                     <td class="td0516">Remarks:</td>
                     <td>&nbsp;</td>
                  </tr>
<%
   Integer[] seqNumRemark = thisrow.getRemarkSeqNumber();
   int remarkNumber = thisrow.getRemarkSeqNumber().length;
   for (int x = 0; x < remarkNumber; x++)
   {
      if (seqNumRemark[x] != null)
      {
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04140102"><b><%= thisrow.getRemark()[x] %></b>&nbsp;</td>
                  </tr>
<%
       }
   }
%>
               </table>
            </span>
         </td>
      </tr>
   </table>
<%
  if (!thisrow.getListComments().isEmpty())
  {
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", thisrow.getListComments());  
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Comments", 0, 1, 6, 1, 0) %>
     <jsp:include page="../../APP/Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>          
<%
}
if (!thisrow.getListURLs().isEmpty())
{
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", thisrow.getListURLs());  
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Attach Documents", 0, 2, 7, 1, 0) %>
     <jsp:include page="../../APP/Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>         
<%
}
}
}
%>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>