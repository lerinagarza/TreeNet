<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "javax.servlet.http.*" %>
<%
//---------------- inqSamples.jsp ---------------------------------------//
//
//  Author :  Tom Haile 6/25/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  1/12/07    TWalton	   Take out the Word Chemical -- just leave Additive
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
      sendExtraOptions = "<option value=\"CtlSampleRequest?requestType=add\">Add a Sample Request";

//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Search Samples");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", sendExtraOptions);
//**************************************************************************//
//**************************************************************************//
// Get Request Session Variables for Display on this Screen
//**************************************************************************
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String sampleNumber       = request.getParameter("sampleNumber");
    if (sampleNumber == null)
       sampleNumber     = "";
   String fromSample         = request.getParameter("fromSample");
    if (fromSample == null)
       fromSample       = "";
   String toSample           = request.getParameter("toSample");
    if (toSample == null)
       toSample         = "";
   String fromReceivedDate   = request.getParameter("fromReceivedDate");
    if (fromReceivedDate == null)
       fromReceivedDate = "";
   String toReceivedDate     = request.getParameter("toReceivedDate");
    if (toReceivedDate == null)
       toReceivedDate   = "";
   String fromShipDate       = request.getParameter("fromShipDate");
    if (fromShipDate == null)
       fromShipDate     = "";
   String toShipDate         = request.getParameter("toShipDate");
    if (toShipDate == null)
       toShipDate       = "";
   String fromDeliveryDate   = request.getParameter("fromDeliveryDate");
    if (fromDeliveryDate == null)
       fromDeliveryDate = "";
   String toDeliveryDate     = request.getParameter("toDeliveryDate");
    if (toDeliveryDate == null)
       toDeliveryDate   = "";
   String customerName       = request.getParameter("customerName");
    if (customerName == null)
       customerName     = "";
   String customerNumber     = request.getParameter("customerNumber");
    if (customerNumber == null)
       customerNumber   = "";
   String formulaName       = request.getParameter("formulaName");
    if (formulaName == null)
       formulaName      = "";
   String resource           = request.getParameter("resource");
    if (resource == null)
       resource         = "";
   String productDescription = request.getParameter("productDescription");
    if (productDescription == null)
       productDescription = "";
   String broker             = request.getParameter("broker");
    if (broker == null)
       broker             = "";
   String lotNumber         = request.getParameter("lotNumber");
     if (lotNumber == null)
       lotNumber = "";       
%>

<html>
  <head>
    <title>Sample Order Inquiry</title>
   <%= JavascriptInfo.getCalendarHead() %>        

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
<body onload="changeList(document.forms['inqSampleRequest'].template)">
<jsp:include page="../../Include/heading.jsp"></jsp:include>

   <form name="inqSampleRequest" action="/web/CtlSampleRequest" method="post">
       <input type="hidden"  name="requestType" value="list">
   
  <table class="table01" cellspacing="0">
    <tr>
      <td style="width:3%">&nbsp;</td>
      <td class="td0414">
        <b>Select a Report Format:&nbsp;&nbsp;</b>
        <select name="template" size=1 onchange="changeList(this)">
          <option value="01">Summary
          <option value="02">Line Item Detail
        </select>
        <select name="version" size=1>
        </select>
      </td>
      <td class="td0414">
        <b>Make Selections and then press:&nbsp;&nbsp;</b><input type="Submit" value="Go">
      </td>
       <td style="width:3%">&nbsp;</td>
    </tr>
  </table>
  <table class="table00" cellspacing="0">
    <tr class="tr02">
      <td class="td0516" colspan="5" style="text-align:center"><b> Search On: </b></td>
    </tr>
    <tr class="tr00">
      <td class="td0414" colspan="2"><b>Choose Sample Request Number:&nbsp;&nbsp;</b></td>
      <td class="td0414" colspan="3">&nbsp;&nbsp;<input size="6" type="text" maxlength="6" name="sampleNumber" value="<%= sampleNumber %>"></td>
    </tr>
       <tr class="tr02">
      <td class="td0516" colspan="5" style="text-align:center"><b> -- OR -- </b></td>
    </tr>
    <tr class="tr01">
      <td class="td0314" colspan="5">
        <fieldset>
          <legend><b> Select Order Status (one or more / If none chosen assume all) </b></legend>
          <table class="table00" cellspacing="0">
            <tr class="tr01">
              <td class="td0412" style="width:20%">
                <input type="checkbox" name="OP">Open
              </td>
              <td class="td0412" style="width:20%">
                <input type="checkbox" name="PD">Pending
              </td>
              <td class="td0412" style="width:20%">
                <input type="checkbox" name="SH">Shipped
              </td>
              <td class="td0412" style="width:20%">
                <input type="checkbox" name="CO">Completed
              </td>
            </tr>
          </table>
        </fieldset>
      </td>
    </tr>
    <tr>
      <td class="td0516"><b>Then choose: </b></td>
      <td class="td0416"><b>FROM</b></td>
      <td class="td0416"><b>TO</b></td>
      <td class="td04140102"><b>Sample Type:</b></td>
      <td class="td04140102"><%= generalInfo[0] %></td>
    </tr>
    <tr>
      <td class="td04140102"><b>Sample Request Number:</b></td>
      <td class="td04140102"><input size="6" type="text"  name="fromSample"
                                                          value="<%= fromSample %>">
      </td>
      <td class="td04140102"><input size="6" type="text" maxlength="9" name="toSample"
                                                                       value="<%= toSample %>">
      </td>
      <td class="td04140102"><b><%//Application:%>&nbsp;</b></td>
      <td class="td04140102">
        <%// generalInfo[2] %>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b>Received Date:</b></td>
      <td class="td04140102">
         <%= HTMLHelpersInput.inputBoxDate("fromReceivedDate",
	                                        fromReceivedDate,
                 	                       "fromRecDateInfo",
                 	                       "N", "N") %>
      </td>
      <td class="td04140102">
         <%= HTMLHelpersInput.inputBoxDate("toReceivedDate",
	                                        toReceivedDate,
                 	                       "toRecDateInfo",
                 	                       "N", "N") %>
      </td>
      <td class="td04140102"><b>Sales Representative:</b></td>
      <td class="td04140102">
        <%= generalInfo[1] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b>Ship Date:</b></td>
      <td class="td04140102">
         <%= HTMLHelpersInput.inputBoxDate("fromShipDate",
	                                        fromShipDate,
                 	                       "fromShipDateInfo",
                 	                       "N", "N") %>      
      </td>
      <td class="td04140102">
         <%= HTMLHelpersInput.inputBoxDate("toShipDate",
	                                        toShipDate,
                 	                       "toShipDateInfo",
                 	                       "N", "N") %>        
      </td>
      <td class="td04140102"><b>Lot Number:</b></td>
      <td class="td04140102">
        <%= HTMLHelpersInput.inputBoxText("lotNumber", lotNumber, "Lot Number", 15, 15, "N", "N") %>
      </td>
<%
//      <td class="td04140102"><b>//Technician:&nbsp;</b></td>
//      <td class="td04140102">
//         // generalInfo[3] 
//         &nbsp; 
//      </td>
%>      
    </tr>
    <tr>
      <td class="td04140102"><b>Delivery Date:</b></td>
      <td class="td04140102">
         <%= HTMLHelpersInput.inputBoxDate("fromDeliveryDate",
	                                        fromDeliveryDate,
                 	                       "fromDelDateInfo",
                 	                       "N", "N") %>           
      </td>
      <td class="td04140102">
         <%= HTMLHelpersInput.inputBoxDate("toDeliveryDate",
	                                        toDeliveryDate,
                 	                       "toDelDateInfo",
                 	                       "N", "N") %>          
      </td>
      <td class="td04140102"><b>Product Group:</b></td>
      <td class="td04140102">
        <%= generalInfo[4] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b><acronym title="Search by partial customer name.">Customer Name:</acronym></b></td>
      <td class="td04140102" colspan="2">
         <input size="30" type="text" maxlength="30" name="customerName">
      </td>
      <td class="td04140102"><b>Product Type:</b></td>
      <td class="td04140102">
        <%= generalInfo[5] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><a class="a04002"
                              href="/web/CtlSampleCustomer"
                              title="Click here to search for customers!" target="_blank">
                              Customer Number:
                             </a></td>
      <td class="td04140102" colspan="2">
        <input size="10" type="text" maxlength="10" name="customerNumber">
      </td>
      <td class="td04140102"><b>Location:</b></td>
      <td class="td04140102">
        <%= generalInfo[13] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b><acronym title="Search by partial Formula name.">Formula Name:</acronym></b></td>
      <td class="td04140102" colspan="2">
        <input size="30" type="text" maxlength="30" name="formulaName">
      </td>
      <td class="td04140102"><b>Cut Size:</b></td>
      <td class="td04140102">
        <%= generalInfo[6] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><a class="a04002"
                              href="/web/CtlRandDFormula"
                              title="Click here to search for formulas!" target="_blank">
                              Formula Number:
                             </a></td>
      <td class="td04140102" colspan="2">
        <input size="10" type="text" maxlength="10" name="formulaNumber">
      </td>
      <td class="td04140102"><b>Additive:</b></td>
      <td class="td04140102">
        <%= generalInfo[7] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b><acronym title="Search by partial Description.">Product Description:</acronym></b></td>
      <td class="td04140102" colspan="2">
        <input size="30" type="text" maxlength="30" name="productDescription">
      </td>
      <td class="td04140102"><b>Color:</b></td>
      <td class="td04140102">
         <%= generalInfo[11] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b>Item Number:</b></td>
      <td class="td04140102" colspan="2">
        <input size="15" type="text" maxlength="15" name="resource">
      </td>
      <td class="td04140102"><b>Flavor:</b></td>
      <td class="td04140102">
        <%= generalInfo[12] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b>Broker Number:</b></td>
      <td class="td04140102" colspan="2">
        <input size="3" type="text" maxlength="3" name="broker">
      </td>
      <td class="td04140102"><b>Fruit Variety:</b></td>
      <td class="td04140102">
        <%= generalInfo[8] %>
      </td>
    </tr>
    <tr>
      <td class="td04140102"><b>Fruit Type:</b></td>
      <td class="td04140102" colspan="2">
        <%= generalInfo[10] %>
      </td>
      <td class="td04140102"><b>Shipped Fruit Variety:</b></td>
      <td class="td04140102">
        <%= generalInfo[9] %>
      </td>
    </tr>

  </table>
   </form>  
<%= JavascriptInfo.getCalendarFoot("inqSampleRequest",
                                         "fromRecDateInfo",
                                         "fromReceivedDate") %>
<%= JavascriptInfo.getCalendarFoot("inqSampleRequest",
                                         "toRecDateInfo",
                                         "toReceivedDate") %>
<%= JavascriptInfo.getCalendarFoot("inqSampleRequest",
                                         "fromShipDateInfo",
                                         "fromShipDate") %>
<%= JavascriptInfo.getCalendarFoot("inqSampleRequest",
                                         "toShipDateInfo",
                                         "toShipDate") %>  
<%= JavascriptInfo.getCalendarFoot("inqSampleRequest",
                                         "fromDelDateInfo",
                                         "fromDeliveryDate") %>
<%= JavascriptInfo.getCalendarFoot("inqSampleRequest",
                                         "toDelDateInfo",
                                         "toDeliveryDate") %>  
 <jsp:include page="../../Include/footer.jsp"></jsp:include>

</body>
</html>
