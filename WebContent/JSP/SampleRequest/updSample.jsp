<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "javax.servlet.http.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- updSample.jsp ---------------------------------------//
//
//  Author :  Teri Walton  7/14/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  1/12/07    TWalton	   Take out the Word Chemical -- just leave Additive
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  8/6/08     TWalton     Changed new Stylesheet - new Look (info on New Machine)
//								Changed to split out the JSP's AND utilitize some of the HTML Helper Class
//------------------------------------------------------------------------------//
   SampleRequestOrder sro = (SampleRequestOrder) request.getAttribute("sro");
   
   

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

  sendExtraOptions = sendExtraOptions + "<option value=\"CtlSampleRequest?requestType=print&sampleNumber=" + sro.getSampleNumber() + "\">Print a Sample Request";


//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Add/Copy/Update Sample");
   String parameterList = "&returnToPage=CtlSampleRequest";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleCustomer?requestType=add\">Add a New Sample Customer");

//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************


   BigDecimal[] quantity = sro.getQuantity();
   BigDecimal[] containerSize = sro.getContainerSize();
   int howManyDetail = sro.getDtlSeqNumber().length;

   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

   String requestType = (String) request.getAttribute("requestType");

// Bring in Customer Information
   SampleRequestCustomer custClass = (SampleRequestCustomer) request.getAttribute("custClass");

  // Default Information to come in open
   String expand1 = "";
   String expandimg1 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand2 = "";
   String expandimg2 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand3 = "";
   String expandimg3 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand4 = "";
   String expandimg4 = "https://image.treetop.com/webapp/minusbox3.gif";

   if (!sro.getStatus().trim().equals("add") &&
       !sro.getStatus().trim().equals("complete") &&
       !sro.getStatus().trim().equals("open"))
   {
      if(sro.getStatus().trim().equals("pending"))
      {
         expand3 = "style=\"display:none\"";
         expandimg3 = "https://image.treetop.com/webapp/plusbox3.gif";
         expand4 = "style=\"display:none\"";
         expandimg4 = "https://image.treetop.com/webapp/plusbox3.gif";

         if (sro.getType().trim().equals("shipped"))
         {
            expand2 = "style=\"display:none\"";
            expandimg2 = "https://image.treetop.com/webapp/plusbox3.gif";
         }
         else
         {
            expand1 = "style=\"display:none\"";
            expandimg1 = "https://image.treetop.com/webapp/plusbox3.gif";
         }
      }
   }
//**************************************************************************//
    String printPage = "CtlSampleRequest?requestType=print";

%>
<html>
<head>
   <%= JavascriptInfo.getCalendarHead() %>        
<%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>   
<style>
<!--
.spanstyle{
position:absolute;
z-index:100;
background-color:EF3FAFF;
width:250px;
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

<title>Update Sample Request</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
   <table class="table00" cellspacing="0">
    <form name="sampleInfo" action="/web/CtlSampleRequest" method="post" >
      <input type="hidden" name="currentLines" value="<%= howManyDetail %>">
      <input type="hidden" name="requestType" value="updateFinish">
<%
//**************************************************************************************************************
//**************************************************************************************************************
    // THIS TEMPLATE IS FOR OPEN STATUS.
//**************************************************************************************************************
//**************************************************************************************************************
if (sro.getStatus().trim().equals("OP"))
{
%>
      <tr>
         <td>&nbsp;</td>
         <td class="td0312" colspan="6">
            &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<i>* Required Fields
            &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;** Required To Move To Pending Status</i>
         </td>
      </tr>
      <tr>
         <td style="width:5%" rowspan="5">&nbsp;</td>
         <td class="td04120102" style="width:20%">
            <acronym title="This number is generated automatically each time a new sample request is started.
You can Refer to this number when you need to locate your sample request in the system.">
               <b>Sample Request Number:</b>
            </acronym>
         </td>
         <td class="td04120102" style="width:20%"> 
           <a class="a0414" href="/web/CtlSampleRequest?requestType=detail&sampleNumber=<%= sro.getSampleNumber() %>" target="_blank"><%= sro.getSampleNumber() %></a>
           <input type="hidden" name="sampleNumber" value="<%= sro.getSampleNumber() %>">
         </td>
         <td class="td04120102">
            <acronym title="Samples have 4 status options:
      Open is for initial entry by the Sales Group
      Pending shows that it is ready to be filled by R&D
      Shipped shows that it has been shipped
               and email confirmations have been sent
      Complete">
               <b>Sample Status:</b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // status drop down list  %>
           <%=generalInfo[9]%>
         </td>
         <td class="td04120102" style="text-align:right" colspan="2" >
            <input type="Submit" value="Save Changes">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="To be completed by technicians.
This drop down categorizes the sample by type of product
  and a general description of how it is to be completed.">
               <b>Sample Type:</b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // sample type drop down list  %>
           <%=generalInfo[0] %>
         </td>
         <td class="td04120102">
            <acronym title="Must be completed by Entry person.
Shows the Sales rep that is responsible for the requesting customer.">
               <b>Sales Representative:</b>
            </acronym>
         </td>
         <td class="td03120102" colspan="3">
<%         // sales rep drop down list  %>
           <%=generalInfo[1] %>&nbsp;&nbsp;<i>*</i>
          </td>
         <td style="width:5%">&nbsp;</td>
      </tr>
      <tr>
         <td class="td0412">
            <acronym title="To be completed by person entering sample.
ALWAYS ask the requestor how is the sample going to be used..
    What is the customer making?">
               <b><%//Application Type:%></b>
            </acronym>&nbsp;
         </td>
         <td class="td0412">
<%         // application type drop down list  
//** Hide - requested Removal 9/29/05 TW Project 7142
%>
           <%//generalInfo[2] %>&nbsp;
         </td>
         <td class="td04120102">
            <acronym title="Enter the name of the person who requested the sample.">
               <b>Sample Requested By:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="3"><input size="20" type="text" maxlength="20" name="requestedBy"
                                                                 value="<%=sro.getWhoRequested().trim()%>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="Automatically filled in with the date and time you started the sample request.">
               <b>Sample Received Date and Time:</b>
            </acronym>
         </td>
         <td class="td03120102">
<%
    String receivedDate = "";
    String[] recDate = GetDate.getDates(sro.getReceivedDate());
       receivedDate = recDate[5];
%>
            <%= HTMLHelpersInput.inputBoxDate("receivedDate",
	                                   receivedDate,
             	                       "recDateInfo", "N", "N") %>  
             	&nbsp;&nbsp;<i>*</i>
            <input size="8"  type="text" maxlength="8"  name="receivedTime" value="<%=sro.getReceivedTime()%>">&nbsp;&nbsp;<font color="#990000"><i>*</i></font>
          </td>
         <td class="td04120102">
            <acronym title="Select the name of the person who received and entered this sample request.">
               <b>Sample Request Received By:</b>
            </acronym>
         </td>
         <td class="td03120102" colspan="3">
      <%       // sample request received by  %>
                       <%=generalInfo[4] %>
                        &nbsp;&nbsp;<i>*</i>
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="Enter the date when you want the sample delivered to the customer.
This will default 10 days after the received date.
This is used by the technicians to determine the best way and when to ship the sample.">
               <b>Sample Delivery Date:</b>
            </acronym>
         </td>
         <td class="td03120102">
<%
    String deliveryDate = "";
    String[] delDate = GetDate.getDates(sro.getDeliveryDate());
      deliveryDate = delDate[5];
%>
            <%= HTMLHelpersInput.inputBoxDate("deliveryDate",
	                                   deliveryDate,
             	                       "delDateInfo", "N", "N") %>           
              &nbsp;&nbsp;<i>*</i>
         </td>
         <td class="td04120102">
            <acronym title="Enter the 3 digit number for the broker who is responsible for managing the requesting customer.">
               <b>Broker Territory:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="2">
           <input size="3" type="text" maxlength="3" name="territory" value="<%=sro.getTerritory()%>">
         </td>
         <td class="td04120102" style="text-align:center">
            <div>
               &nbsp;&nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Print"
                            onClick="viewmenu(document.all[this.sourceIndex+1]);">
            </div>
            <span class="spanstyle" style="display:none" style=&{head1}; >
               &nbsp;<a href="<%= printPage %>&sampleNumber=<%=sro.getSampleNumber()%>" target="_blank" >Print Sample Request</a><br>
               &nbsp;<a href="<%= printPage %>remarks&sampleNumber=<%=sro.getSampleNumber()%>" target="_blank" >Print Sample Request with Remarks</a><br>
            </span>
         </td>
      </tr>
   </table>

<%           // Shipping Information Fields not used in this Template  %>
<%                     // ship via  %>
                        <input type="hidden" name="shipVia" value="<%=sro.getShipVia().trim()%>">
<%                     // ship how  %>
                        <input type="hidden" name="shipHow" value="<%=sro.getShipHow().trim()%>">
<%                     // tracking Number  %>
                        <input type="hidden" name="trackingNumber" value="<%=sro.getTrackingNumber().trim()%>">
<%                     // ship Date  %>
                        <input type="hidden" name="shipDate" value="<%=sro.getShippingDate()%>">

   <table class="table00">
      <tr class="tr02">
         <td class="td03200102">
            <font style="color:#990000;
                         font-weight:bold;
                         font-family:arial;
                         font-size:12pt;
                         text-align:left;">
                   &nbsp;<img src="<%= expandimg1 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(1,3);">
                   Shipping Information
            </font>
            <span <%= expand1 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:3%" rowspan="25">&nbsp;</td>
                     <td class="td04120102" style="width:25%">
                        <a href="/web/CtlSampleCustomer"
                           title="If you know the Sample customer number enter it here.
The address and contact information will be retrieved from the sample customer database when you click on the Save Changes button.
If you do not know the number you can click on the underlined field so you can search the list for your customer.
If your customer is not in the database you may enter a new one from the customer screen.    " target="_blank">
                           Customer Number:
                        </a>
                     </td>
                     <td class="td03120102" style="width:30%">
                        <input size="9" type="text" maxlength="9" name="custNumber"
                                                                    value="<%= sro.getCustNumber() %>">
                        &nbsp;&nbsp;<i>**</i>
                     </td>
                     <td class="td04120102" style="width:25%">
                        <b>SHIP TO:</b>
                     </td>
                     <td class="td04120102"><%= custClass.getName() %>&nbsp;</td>
                     <td style="width:3%" rowspan="25">&nbsp;</td>
                  </tr>
<%
   String custContact = sro.getCustContact().trim();
   String custContactPhone = sro.getCustContactPhone().trim();
   String custContactEmail = sro.getCustContactEmail().trim();
   if (custContact.length() == 0)
   {
      custContact = custClass.getContact();
      if (custContactPhone.length() == 0)
         custContactPhone = custClass.getContactPhone();
      if (custContactEmail.length() == 0)
         custContactEmail = custClass.getContactEmail();
   }
%>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Use the name pulled from the customer database or enter a new Customer contact in this field.
If you enter a name here it is saved with the sample request only.
It will not update the sample customer master.">
               Customer Contact:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="30" type="text" maxlength="30" name="customerContact"
                                                                    value="<%= custContact %>">
                     </td>
                     <td class="td04120102">Customer Address 1:</td>
                     <td class="td04120102"><%= custClass.getAddress1() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title=" Use the phone number pulled from the customer database or enter a new phone number in this field.
If you enter a number here it is saved with the sample request only.
It will not update the sample customer master.">
              Customer Contact Phone:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="contactPhone"
                                                                    value="<%= custContactPhone %>">
                     </td>
                     <td class="td04120102">Customer Address 2:</td>
                     <td class="td04120102"><%= custClass.getAddress2() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Use the email address pulled from the customer database or enter a new email address in this field.  If you enter an address here it is saved with the sample request only.
It will not update the sample customer master.
At this point, this is informational only.
Emails are not currently being sent to this address automatically.
If you want to send an email to this address you must enter it into the Additional emails fields.">
              Customer Contact E-Mail:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="40" type="text" maxlength="100" name="contactEmail"
                                                                    value="<%= custContactEmail %>">
                     </td>
                     <td class="td04120102">Customer City and State:</td>
                     <td class="td04120102"><%= custClass.getCity() %>&nbsp;<%= custClass.getState() %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter who you want the sample Marked for.
This will be printed on the packing slip of the sample and delivered to this person.">
              Mark To (Attn) Contact (if different):
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="30" type="text" maxlength="30" name="attnContact"
                                                                    value="<%=sro.getAttnContact().trim()%>">
                     </td>
<%
   String zipCode = "";
   String zipExtention = "";
   if (custClass.getZip() != 0)
      zipCode      = "" + custClass.getZip();
   if (zipCode.length() == 4)
      zipCode      = "0" + zipCode;
   if (zipCode.length() == 3)
      zipCode      = "00" + zipCode;
   if (zipCode.length() == 2)
      zipCode      = "000" + zipCode;
   if (zipCode.length() == 1)
      zipCode      = "0000" + zipCode;
   if (custClass.getZipExtention() != 0)
      zipExtention = " - " + custClass.getZipExtention();
%>
                     <td class="td04120102">Customer Zip Code and Extension:</td>
                     <td class="td04120102"><%= zipCode %>&nbsp;<%= zipExtention %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the phone number of the Mark to person.
This will print on the packing slip.">
              Mark To Contact Phone:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="attnPhone"
                                                                    value="<%=sro.getAttnContactPhone().trim()%>">
                     </td>
                     <td class="td04120102">Customer Postal Code and Country:</td>
                     <td class="td04120102"><%= custClass.getForeignPostalCode() %>&nbsp;<%= custClass.getForeignCountry() %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the email address for the Mark to person.
At this point, this is informational only.
Emails are not currently being sent to this address automatically.
If you want to send an email to this address you must enter it into the Additional emails fields.">
              Mark To Contact E-Mail:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="40" type="text" maxlength="100" name="attnEmail"
                                                                    value="<%=sro.getAttnContactEmail().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>

                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="To be entered by the technician when the sample has been shipped and an estimated charges is known.">
              Shipping Charge:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="20" type="text" maxlength="20" name="shipCharge"
                                                                    value="<%=sro.getShippingCharge().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
              G/L Account Number:
                     </td>
                     <td class="td04120102">
<%
   String displayAcct = "";
   // Build Drop Down List for Display
   try
   {
      Vector dds = new Vector();
 //     DropDownSingle record = new DropDownSingle();
 //     record.setValue("025-120-109-3108");
   //   record.setDescription("025-120-109-3108");
  //    dds.addElement(record);
 //     record = new DropDownSingle();
 //     record.setValue("025-120-109-3129");
 //     record.setDescription("025-120-109-3129");
 //     dds.addElement(record);
 //     record = new DropDownSingle();
 //     record.setValue("001-130-103-3132");
 //     record.setDescription("001-130-103-3132"); 
 //     dds.addElement(record);
 //    record = new DropDownSingle();
 //     record.setValue("001-140-121-3132");
 //     record.setDescription("001-140-121-3132"); 
 //     dds.addElement(record);
      DropDownSingle record = new DropDownSingle();
      record.setValue("61140-125-109");
      record.setDescription("61140-125-109 Ingredient");
      dds.addElement(record);
      record = new DropDownSingle();
      record.setValue("61200-125-103");
      record.setDescription("61200-125-103 Foodservice/Fresh");
      dds.addElement(record);
//      record = new DropDownSingle();
//      record.setValue("61200-125-103");
//      record.setDescription("61200-125-103 Fresh"); 
 //     dds.addElement(record);
      record = new DropDownSingle();
      record.setValue("61140-150-105");
      record.setDescription("61140-150-105 Contract Manufacturing");
      dds.addElement(record);
      
      displayAcct = DropDownSingle.buildDropDown(dds, "acctNumber", sro.getGlAccountNumber().trim(), "None", "N", "N");
      
   }
   catch(Exception e)
   {
      // Catch any problems
   }
%>                     
                        <%= displayAcct %>                     
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the 5 digit number.  This number is the 2 digit product line number of the product being sampled combined with the 3 digit broker number.  Ex. 20155 ">
              G/L Account Miscellaneous:
            </acronym>
                     </td>
                     <td class="td03120102">
                        <input size="10" type="text" maxlength="10" name="acctMisc"
                                                                    value="<%=sro.getGlAccountMisc().trim()%>">
                            &nbsp;&nbsp;<i>**</i>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Shipping Location:</td>
                     <td class="td03120102">
                        <%= generalInfo[10] %>
                        &nbsp;&nbsp;<i>**</i>
                     </td>
                     <td class="td04120102" colspan = "2">&nbsp;</td>
                  </tr>
<%
   if (0 == 1)
   {
%>                  
                  <tr class="tr00">
                     <td class="td04120102">
                     <%//Technician:
                     //** Hide - requested Removal 9/29/05 TW Project 7142
                     %>
                     </td>
                     <td class="td04120102">
                        <%// generalInfo[3] %>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
<%
   }
//** Hide - requested Removal 9/29/05 TW Project 7142
if (0 == 1)
{
%>                  
                  <tr class="tr00">
                     <td class="td04120102">
           <acronym title="Click on the documents that you would like to have shipped with this sample request.
You can choose more than one.">
              Documents:
            </acronym>
                     </td>
                     <td class="td04120102" colspan="3">
                        <%// generalInfo[7] %>
                     </td>
                  </tr>
<%
}
%>                  
                  <tr class="tr00">
                     <td class="td04120102" rowspan="5">
           <acronym title="Enter any additional email address of the people who you want to receive and notification that the sample has been shipped.
This email will include details of the request.
At this time the Sales representative, Who entered the sample<%// and the shipping technician%> receive the emails automatically. ">
              Additional E-Mail When Shipped:
            </acronym>
                     </td>
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail1"
                                                                      value="<%=sro.getEmailWhenShipped1().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail2"
                                                                      value="<%=sro.getEmailWhenShipped2().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail3"
                                                                      value="<%=sro.getEmailWhenShipped3().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail4"
                                                                      value="<%=sro.getEmailWhenShipped4().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td0412" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail5"
                                                                      value="<%=sro.getEmailWhenShipped5().trim()%>">
                     </td>
                  </tr>
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
                   &nbsp;<img src="<%= expandimg2 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(2,4);">
                   Line Items
            </font>
            <span <%= expand2 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:4%">
                     </td>
                     <td class="td04120102" colspan="4">
<%
   String viewLotChecked = "";
   if (sro.getViewLot() != null &&
       sro.getViewLot().equals("Y"))
     viewLotChecked = "checked";
%>
                        <input type="checkbox" name="viewLot" <%= viewLotChecked %>>&nbsp; Check if you want the Lot Numbers to be seen when Printed and E-mailed.
                     </td>
                     <td style="width:4%">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td>
                     </td>
                     <td class="td04120102" colspan="4">
<%
   String viewVarietyChecked = "";
   if (sro.getViewVariety() != null &&
       sro.getViewVariety().equals("Y"))
     viewVarietyChecked = "checked";
%>
                        <input type="checkbox" name="viewVariety" <%= viewVarietyChecked %>>&nbsp; Check if you want the Shipped Fruit Variety to be seen when Printed and E-mailed.
                     </td>
                     <td>
                     </td>
                  </tr>
<% //detail lines

   int y = 0;
   for(int x = 0 ; x < howManyDetail; x++ )
   {
       y = y + 1;
%>
<%           //  Fields not used in this Template  %>

<%           //  Sequence Number %>
                  <input type="hidden" name="<%=y%>sequence"      value="<%= sro.getDtlSeqNumber()[x] %>">
<%           //  Formula Number  %>
                  <input type="hidden" name="<%=y%>formula"       value="<%= sro.getFormulaNumber()[x] %>">
<%           //  Shipped Fruit Variety  %>
                  <input type="hidden" name="shippedFruitVariety" value="<%=sro.getShippedFruitVariety()[x].trim() %>">
                  <tr class="tr05">


                     <td style="width:4%"></td>
                     <td class="td0014000005" style="width:4%; text-align:center">
           <acronym title="Check this box if you have entered a line item and decide to have it removed from the request.
The line item will be deleted when you click on the save changes button.
The sample must be in the Open status for this option to work.">
              <b>Delete</b>
            </acronym>
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="How many of this particular line item would you liked shipped?">
              <b>Quantity</b>
            </acronym>
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="Enter the number of the size of the sample.">
              <b>Container Size</b>
            </acronym>
                     </td>
                     <td class="td0014000005">
           <acronym title="Select the measurement you want the technicians to use when filling the sample.">
              <b>Unit of Measure</b>
            </acronym>
                     </td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>

                  <tr class="tr00">
                     <td class="td041CC001" rowspan="15">&nbsp;</td>
                     <td class="td041CC002">
                        <input type="checkbox" name="<%=y%>deleteline">
                     </td>
                     <td class="td041CC002">
                        <input size="3" type="text" maxlength="3" name="<%=y%>quantity"
                                                                  value="<%=quantity[x]%>">
                     </td>
                     <td class="td041CC002">
                        <input size="3" type="text" maxlength="3" name="<%=y%>containerSize"
                                                                  value="<%=sro.getContainerSize()[x]%>">
                     </td>
                     <td class="td04120102">
<%                     // unit of measure  %>
                       <%=generalInfo[20 + x] %>
                     </td>
                     <td class="td041CC001" rowspan="15">&nbsp;</td>
                 </tr>
                  <tr class="tr00">
                     <td rowspan="14">&nbsp;</td>
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items being sampled.
You must select the description that best categorizes the item you are sampling.
This option represents the different product lines we sell.
This is used for reporting samples by group.">
              Product Group:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // product Group  %>
                       <%=generalInfo[20 + howManyDetail + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items.
This will make the descriptions that are printed on the sample requests more clear.">
              Fruit Type:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // fruit type  %>
                       <%=generalInfo[20 + (howManyDetail * 7) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items being sampled.
You must select the description that best describes how the product is processed.
If an option is not available then contact the help desk.  ">
              Product Type:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // product Type  %>
                       <%=generalInfo[20 + (howManyDetail * 2) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required for non concentrate items.
Find the best option for the size of the product you are sampling ">
              Cut Size:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // cut size  %>
                       <%=generalInfo[20 + (howManyDetail * 3) + x] %>
                     </td>
                  </tr>
               <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items where preservatives might be used.
Select the preservative that will be used for this sample.">
              Additive:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // chemical **word chemical taken out 1/8/07 tw**  additive  %>
                       <%=generalInfo[20 + (howManyDetail * 6) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required if color is different than standard.
Select the option which best describes the color the sample should be.">
              Color:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // color  %>
                       <%=generalInfo[20 + (howManyDetail * 4) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required if flavor is different than standard .
Select the option which best describes what the flavor of the product should be">
              Flavor:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // flavor  %>
                       <%=generalInfo[20 + (howManyDetail * 5) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required unless customer specifically requests.
Completed by the person entering the sample request.
If the customer requests a specific variety then select it here.
If not then you may leave this field blank.">
              Fruit Variety:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // fruit variety  %>
                       <%=generalInfo[20 + (howManyDetail * 8) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required, if you used the fields above to identify the product you want sampled.
If the item is a non stock item, enter your description for what you want sampled here.
Other wise leave this field blank.">
              Item Description:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="42" type="text" maxlength="42" name="<%=y%>itemDescription"
                                                                    value="<%=sro.getItemDescription()[x].trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
If the item is a non stock item, enter more information about the product you would like sampled here.
Other wise leave this field blank.">
              Additional Description:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="30" type="text" maxlength="30" name="<%=y%>addDescription"
                                                                    value="<%=sro.getAdditionalDescription()[x].trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
IF you know the standard resource number of the item you are sampling enter it here.
The resource description will be automatically entered into the Item Description field.">
              Resource Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="<%=y%>resource"
                                                                    value="<%=sro.getResource()[x].trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.  Concentrate only.
If a brix is requested that is different than our standard items.
Enter the requested brix level here.">
              Brix Level:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="<%=y%>brixLevel"
                                                                    value="<%=sro.getBrixLevel()[x]%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
Enter the lot number of the product you want sampled.
This is usually for pre-shipment samples of concentrate.">
              Lot Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="<%=y%>lotNumber"
                                                                    value="<%= sro.getLotNumber()[x].trim() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
Enter the Tree Top spec number of the product you are sampling.
Example BAM01, TTA01.">
              Spec Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="5" type="text" maxlength="5" name="<%=y%>spec"
                                                                    value="<%=sro.getSpecNumber()[x].trim()%>">
                     </td>
                  </tr>

<%  // end detail lines
   }
%>
                  <tr class="tr02">
                  <td colspan="7"></td>
                  </tr>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="3"><b>How Many Additional Lines Do you Need?</b></td>
                     <td class="td04120102">
                        <input size="3" type="text" maxlength="3" name="additionalLines"
                                                                    value="0">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
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
                   &nbsp;<img src="<%= expandimg3 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(3,5);">
                   Comments/Remarks
            </font>
            <span <%= expand3 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr05">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0014000005" colspan="2"><b>Comments/Internal Communication Only:</b></td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%  // comment lines
   y = 0;
   for(int x = 0; x < 4; x++)
   {
      y = y +1;
%>
                  <input type="hidden" name="<%=y%>commentDate" value="<%= sro.getCommentDate()[x] %>">
                  <input type="hidden" name="<%=y%>commentTime" value="<%= sro.getCommentTime()[x] %>">

                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        <input size="60" type="text" maxlength="60" name="<%=y%>comment"
                                                                    value="<%=sro.getComment()[x].trim()%>">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
   }
   
%>

                  <tr class="tr05">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0014000005" colspan="2"><b>Remarks(Everyone can see):</b></td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%  // remark lines
   y = 0;
   for(int x = 0; x < 6; x++)
   {
      y = y + 1;
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        <input size="60" type="text" maxlength="60" name="<%=y%>remark"
                                                                     value="<%=sro.getRemark()[x].trim()%>">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
   }
%>

               </table>
            </span>
         </td>
      </tr>
<%= JavascriptInfo.getCalendarFoot("sampleInfo",
                                         "recDateInfo",
                                         "receivedDate") %>  
<%= JavascriptInfo.getCalendarFoot("sampleInfo",
                                         "delDateInfo",
                                         "deliveryDate") %>                                                   
<%
}
//******************************************************************************************************
//******************************************************************************************************
  // THIS TEMPLATE IS FOR PENDING STATUS.
//******************************************************************************************************
//******************************************************************************************************
if (sro.getStatus().trim().equals("PD"))
{
%>
      <tr>
         <td></td>
         <td class="td0312" colspan="5">
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>** Required To Move To Shipped Status</i>
         </td>
         <td></td>
      </tr>
      <tr>
         <td style="width:5%" rowspan="5"></td>
         <td class="td04120102" style="width:20%">
            <acronym title="This number is generated automatically each time a new sample request is started.
You can Refer to this number when you need to locate your sample request in the system.">
               <b>Sample Request Number:</b>
            </acronym>
         </td>
         <td class="td04120102" style="width:20%"> 
            <a class="a0414" href="/web/CtlSampleRequest?requestType=detail&sampleNumber=<%= sro.getSampleNumber() %>" target="_blank"><%= sro.getSampleNumber() %></a>
         <input type="hidden" name="sampleNumber" value="<%= sro.getSampleNumber() %>">
         </td>
         <td class="td04120102">
            <acronym title="Samples have 4 status options:
      Open is for initial entry by the Sales Group
      Pending shows that it is ready to be filled by R&D
      Shipped shows that it has been shipped
               and email confirmations have been sent
      Complete">
               <b>Sample Status:</b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // status drop down list  %>
           <%=generalInfo[9]%>
         </td>
         <td class="td04120102" colspan="2" >
            <input type="Submit" value="Save Changes">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="To be completed by technicians.
This drop down categorizes the sample by type of product
  and a general description of how it is to be completed.">
               <b>Sample Type:</b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // sample type Drop Down List  %>
           <%= generalInfo[0] %>&nbsp;&nbsp;<i>**</i>
         </td>
         <td class="td04120102">
            <acronym title="Must be completed by Entry person.
Shows the Sales rep that is responsible for the requesting customer.">
               <b>Sales Representative:</b>
            </acronym>
          </td>
         <td class="td04120102" colspan="2">
<%         // sales rep drop down list  %>
          <%= generalInfo[1] %>
           <input type="hidden" name="sales" value="<%= sro.getSalesRep().trim() %>">
          </td>
         <td style="width:5%">&nbsp;
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="To be completed by person entering sample.
ALWAYS ask the requestor how is the sample going to be used..
    What is the customer making?">
               <b><%//Application Type:%></b>
            </acronym>&nbsp;
         </td>
         <td class="td04120102">
<%         // application type drop down list 
//** Hide - requested Removal 9/29/05 TW Project 7142
%>
           <%// generalInfo[2] %>
<%//           <input type="hidden" name="appType" value=" sro.getApplication().trim() "%>
         </td>
         <td class="td04120102">
           <acronym title="Enter the name of the person who requested the sample.">
               <b>Sample Requested By:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="3">
             <%=sro.getWhoRequested().trim()%>
             <input type="hidden" name="requestedBy" value="<%=sro.getWhoRequested().trim()%>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
       <acronym title="Automatically filled in with the date and time you started the sample request.">
               <b>Sample Received Date and Time:</b>
            </acronym>
         </td>
         <td class="td04120102">
            <%=sro.getReceivedDate()%>&nbsp;-&nbsp;<%=sro.getReceivedTime()%>
            <input type="hidden" name="receivedDate" value="<%=sro.getReceivedDate()%>">
            <input type="hidden" name="receivedTime" value="<%=sro.getReceivedTime()%>">
          </td>
         <td class="td04120102">
            <acronym title="Select the name of the person who received and entered this sample request.">
               <b>Sample Request Received By:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="3">
<%                     // sample request received by  %>
                       <%=generalInfo[4] %>
         </td>

      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="Enter the date when you want the sample delivered to the customer.
This will default 10 days after the received date.
This is used by the technicians to determine the best way and when to ship the sample.">
               <b>Sample Delivery Date:</b>
            </acronym>
         </td>
         <td class="td04120102">
            <%=sro.getDeliveryDate()%>
            <input type="hidden" name="deliveryDate" value="<%=sro.getDeliveryDate()%>">
          </td>
         <td class="td04120102">
            <acronym title="Enter the 3 digit number for the broker who is responsible for managing the requesting customer.">
               <b>Broker Territory:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="2">
           <%=sro.getTerritory()%>
            <input type="hidden" name="territory" value="<%=sro.getTerritory()%>">
         </td>
         <td class="td04120102">
            <div>
               &nbsp;&nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Print"
                            onClick="viewmenu(document.all[this.sourceIndex+1]);">
            </div>
            <span class="spanstyle" style="display:none" style=&{head1}; >
               &nbsp;<a href="<%= printPage %>&sampleNumber=<%=sro.getSampleNumber()%>" target="_blank" >Print Sample Request</a><br>
               &nbsp;<a href="<%= printPage %>remarks&sampleNumber=<%=sro.getSampleNumber()%>" target="_blank" >Print Sample Request with Remarks</a><br>
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
                   &nbsp;<img src="<%= expandimg1 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(1,3);">
                   Shipping Information
            </font>
            <span <%= expand1 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:3%" rowspan="25">&nbsp;</td>
                     <td class="td04120102" style="width:25%">Customer Number:</td>
                     <td class="td04120102" style="width:30%">
                        <%= custClass.getCustNumber() %>
                        <input type="hidden" name="custNumber" value="<%= sro.getCustNumber() %>">
                     </td>
                     <td class="td04120102" style="width:25%"><b>SHIP TO:</b></td>
                     <td class="td04120102"><%= custClass.getName() %>&nbsp;</td>
                     <td style="width:3%" rowspan="25">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Use the name pulled from the customer database or enter a new Customer contact in this field.
If you enter a name here it is saved with the sample request only.
It will not update the sample customer master.">
               Customer Contact:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getCustContact().trim()%>
                        <input type="hidden" name="customerContact" value="<%=sro.getCustContact().trim()%>">
                     </td>
                     <td class="td04120102">Customer Address 1:</td>
                     <td class="td04120102"><%= custClass.getAddress1() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title=" Use the phone number pulled from the customer database or enter a new phone number in this field.
If you enter a number here it is saved with the sample request only.
It will not update the sample customer master.">
              Customer Contact Phone:
            </acronym>
                   </td>
                     <td class="td04120102">
                        <%=sro.getCustContactPhone().trim()%>
                        <input type="hidden" name="contactPhone" value="<%=sro.getCustContactPhone().trim()%>">
                     </td>
                     <td class="td04120102">Customer Address 2:</td>
                     <td class="td04120102"><%= custClass.getAddress2() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Use the email address pulled from the customer database or enter a new email address in this field.  If you enter an address here it is saved with the sample request only.
It will not update the sample customer master.
At this point, this is informational only.
Emails are not currently being sent to this address automatically.
If you want to send an email to this address you must enter it into the Additional emails fields.">
              Customer Contact E-Mail:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getCustContactEmail().trim()%>
                        <input type="hidden" name="contactEmail" value="<%=sro.getCustContactEmail().trim()%>">
                     </td>
                     <td class="td04120102">Customer City and State:</td>
                     <td class="td04120102"><%= custClass.getCity() %>&nbsp;<%= custClass.getState() %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter who you want the sample Marked for.
This will be printed on the packing slip of the sample and delivered to this person.">
              Mark To (Attn) Contact (if different):
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAttnContact().trim()%>
                        <input type="hidden" name="attnContact" value="<%=sro.getAttnContact().trim()%>">
                     </td>
<%
   String zipCode = "";
   String zipExtention = "";
   if (custClass.getZip() != 0)
      zipCode      = "" + custClass.getZip();
   if (zipCode.length() == 4)
      zipCode      = "0" + zipCode;
   if (zipCode.length() == 3)
      zipCode      = "00" + zipCode;
   if (zipCode.length() == 2)
      zipCode      = "000" + zipCode;
   if (zipCode.length() == 1)
      zipCode      = "0000" + zipCode;
   if (custClass.getZipExtention() != 0)
      zipExtention = " - " + custClass.getZipExtention();
%>
                     <td class="td04120102">Customer Zip Code and Extension:</td>
                     <td class="td04120102"><%= zipCode %>&nbsp;<%= zipExtention %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the phone number of the Mark to person.
This will print on the packing slip.">
              Mark To Contact Phone:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAttnContactPhone().trim()%>
                        <input type="hidden" name="attnPhone" value="<%=sro.getAttnContactPhone().trim()%>">
                     </td>
                     <td class="td04120102">Customer Postal Code and Country:</td>
                     <td class="td04120102"><%= custClass.getForeignPostalCode() %>&nbsp;<%= custClass.getForeignCountry() %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the email address for the Mark to person.
At this point, this is informational only.
Emails are not currently being sent to this address automatically.
If you want to send an email to this address you must enter it into the Additional emails fields.">
              Mark To Contact E-Mail:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAttnContactEmail().trim()%>
                        <input type="hidden" name="attnEmail" value="<%=sro.getAttnContactEmail().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>

                  <tr class="tr00">
                     <td class="td04120102">
    <acronym title="To be entered by the technician when the sample has been shipped and an estimated charges is known.">
              Shipping Charge:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getShippingCharge().trim()%>
                        <input type="hidden" name="shipCharge" value="<%=sro.getShippingCharge().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Ship Via:</td>
                     <td class="td03120102">
<%                     // ship via  %>
                       <%=generalInfo[5] %>&nbsp;&nbsp;<i>**</i>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Ship How:</td>
                     <td class="td03120102">
<%                     // ship how  %>
                       <%=generalInfo[6] %>&nbsp;&nbsp;<i>**</i>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Tracking Number:</td>
                     <td class="td04120102">
                        <input size="30" type="text" maxlength="30" name="trackingNumber"
                                                                    value="<%=sro.getTrackingNumber().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">G/L Account Number:</td>
                     <td class="td04120102">
                        <%=sro.getGlAccountNumber().trim()%>
                        <input type="hidden" name="acctNumber" value="<%=sro.getGlAccountNumber().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the 5 digit number.  This number is the 2 digit product line number of the product being sampled combined with the 3 digit broker number.  Ex. 20155 ">
              G/L Account Miscellaneous:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getGlAccountMisc().trim()%>
                        <input type="hidden" name="acctMisc" value="<%=sro.getGlAccountMisc().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Shipping Location:</td>
                     <td class="td04120102">
                         <% // shipping location drop down list  %>
                         <%= generalInfo[10] %>
                         <input type="hidden" name="location" value="<%=sro.getLocation().trim()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Shipping Date:</td>
                     <td class="td03120102">
<%
    String shipDate = "";
    String[] shippingDate = GetDate.getDates(sro.getShippingDate());
      shipDate = shippingDate[5];
%>
            <%= HTMLHelpersInput.inputBoxDate("shipDate",
	                                   shipDate,
             	                       "shipDateInfo", "N", "N") %>   
                             &nbsp;&nbsp;<i>**</i>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
                     <%//Technician:
                     //** Hide - requested Removal 9/29/05 TW Project 7142
                     %>
                     </td>
                     <td class="td04120102">
                        <%// generalInfo[3] &nbsp;&nbsp;<font color="#990000"><i>**</i></font>%>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
<%
//** Hide - requested Removal 9/29/05 TW Project 7142
if (0 == 1)
{
%>                  
                  <tr class="tr00">
                     <td class="td04120102">
           <acronym title="Click on the documents that you would like to have shipped with this sample request.
You can choose more than one.">
              Documents:
            </acronym>
                     </td>
                     <td class="td04120102" colspan="3">
                        <%// generalInfo[7] %>
                     </td>
                  </tr>
<%
}
%>                  
                  <tr class="tr00">
                     <td class="td04120102" rowspan="5">
           <acronym title="Enter any additional email address of the people who you want to receive and notification that the sample has been shipped.
This email will include details of the request.
At this time the Sales representative, Who entered the sample and the shipping technician receive the emails automatically. ">
              Additional E-Mail When Shipped:
            </acronym>
                     </td>
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail1"
                                                                      value="<%=sro.getEmailWhenShipped1().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail2"
                                                                      value="<%=sro.getEmailWhenShipped2().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail3"
                                                                      value="<%=sro.getEmailWhenShipped3().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail4"
                                                                      value="<%=sro.getEmailWhenShipped4().trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input size="100" type="text" maxlength="100" name="addEmail5"
                                                                      value="<%=sro.getEmailWhenShipped5().trim()%>">
                     </td>
                  </tr>
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
                   &nbsp;<img src="<%= expandimg2 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(2,4);">
                   Line Items
            </font>
            <span <%= expand2 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:4%">
                     </td>
                     <td class="td04120102" colspan="4">
<%
   String viewLotChecked = "";
   if (sro.getViewLot() != null &&
       sro.getViewLot().equals("Y"))
     viewLotChecked = "checked";
%>
                        <input type="checkbox" name="viewLot" <%= viewLotChecked %>>&nbsp; Check if you want the Lot Numbers to be seen when Printed and E-mailed.
                     </td>
                     <td style="width:4%">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td>
                     </td>
                     <td class="td04120102" colspan="4">
<%
   String viewVarietyChecked = "";
   if (sro.getViewVariety() != null &&
       sro.getViewVariety().equals("Y"))
     viewVarietyChecked = "checked";
%>
                        <input type="checkbox" name="viewVariety" <%= viewVarietyChecked %>>&nbsp; Check if you want the Shipped Fruit Variety to be seen when Printed and E-mailed.
                     </td>
                     <td>
                     </td>
                  </tr>

<% //detail lines

   int y = 0;
   for(int x = 0 ; x < howManyDetail; x++ )
   {
       y = y + 1;
%>
                  <tr class="tr05">
                    <td style="width:4%">
                     <input type="hidden" name="<%=y%>sequence" value="<%= sro.getDtlSeqNumber()[x] %>">
                     </td>
                     <td class="td0014000005" style="width:4%; text-align:center">
           <acronym title="Check this box if you have entered a line item and decide to have it removed from the request.
The line item will be deleted when you click on the save changes button.
The sample must be in the Open status for this option to work.">
              Delete
            </acronym>
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="How many of this particular line item would you liked shipped?">
              Quantity
            </acronym>
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="Enter the number of the size of the sample.">
              Container Size
            </acronym>
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="Select the measurement you want the technicians to use when filling the sample.">
              Unit of Measure
            </acronym>
                     </td>
                     <td class="td0014000005" style="width:4%">&nbsp;</td>
                  </tr>

                  <tr class="tr00">
                     <td class="td04120102" rowspan="17">&nbsp;</td>
                     <td class="td04120102" style="text-align:center">
                        <input type="checkbox" name="<%=y%>deleteline">
                     </td>
                     <td class="td04120102" style="text-align:center">
                        <input size="3" type="text" maxlength="3" name="<%=y%>quantity"
                                                                  value="<%=quantity[x]%>">
                     </td>
                     <td class="td04120102" style="text-align:center">
                        <input size="3" type="text" maxlength="3" name="<%=y%>containerSize"
                                                                  value="<%=sro.getContainerSize()[x]%>">
                     </td>
                     <td class="td04120102">
<%                     // unit of measure  %>
                       <%=generalInfo[20 + x] %>
                     </td>
                     <td class="td041201" rowspan="17">&nbsp;</td>
                 </tr>
                  <tr class="tr00">
                     <td rowspan="16">&nbsp;</td>
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items being sampled.
You must select the description that best categorizes the item you are sampling.
This option represents the different product lines we sell.
This is used for reporting samples by group.">
              Product Group:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // product Group  %>
                       <%=generalInfo[20 + howManyDetail + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items.
This will make the descriptions that are printed on the sample requests more clear.">
              Fruit Type:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // fruit type  %>
                       <%=generalInfo[20 + (howManyDetail * 7) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items being sampled.
You must select the description that best describes how the product is processed.
If an option is not available then contact the help desk.  ">
              Product Type:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // product Type  %>
                       <%=generalInfo[20 + (howManyDetail * 2) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required for non concentrate items.
Find the best option for the size of the product you are sampling ">
              Cut Size:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // cut size  %>
                       <%=generalInfo[20 + (howManyDetail * 3) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items where preservatives might be used.
Select the preservative that will be used for this sample.">
              Additive:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // chemical **word chemical taken out 1/12/07 tw**  additive  %>
                       <%=generalInfo[20 + (howManyDetail * 6) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required if color is different than standard.
Select the option which best describes the color the sample should be.">
              Color:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // color  %>
                       <%=generalInfo[20 + (howManyDetail * 4) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required if flavor is different than standard .
Select the option which best describes what the flavor of the product should be">
              Flavor:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // flavor  %>
                       <%=generalInfo[20 + (howManyDetail * 5) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required unless customer specifically requests.
Completed by the person entering the sample request.
If the customer requests a specific variety then select it here.
If not then you may leave this field blank.">
              Fruit Variety:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // fruit variety  %>
                       <%=generalInfo[20 + (howManyDetail * 8) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for shipping technician if known.
Completed by the technician.
Technician selects the variety of fruit used to fill the sample.
Not required on concentrate samples.">
              Shipped Fruit Variety:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // shipped fruit variety  %>
                       <%=generalInfo[20 + (howManyDetail * 9) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required, if you used the fields above to identify the product you want sampled.
If the item is a non stock item, enter your description for what you want sampled here.
Other wise leave this field blank.">
              Item Description:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="42" type="text" maxlength="42" name="<%=y%>itemDescription"
                                                                    value="<%=sro.getItemDescription()[x].trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
If the item is a non stock item, enter more information about the product you would like sampled here.
Other wise leave this field blank.">
              Additional Description:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="30" type="text" maxlength="30" name="<%=y%>addDescription"
                                                                    value="<%=sro.getAdditionalDescription()[x].trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
IF you know the standard resource number of the item you are sampling enter it here.
The resource description will be automatically entered into the Item Description field.">
              Resource Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="<%=y%>resource"
                                                                    value="<%=sro.getResource()[x].trim()%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.  Concentrate only.
If a brix is requested that is different than our standard items.
Enter the requested brix level here.">
              Brix Level:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="<%=y%>brixLevel"
                                                                    value="<%=sro.getBrixLevel()[x]%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
Enter the lot number of the product you want sampled.
This is usually for pre-shipment samples of concentrate.">
              Lot Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="15" type="text" maxlength="15" name="<%=y%>lotNumber"
                                                                    value="<%= sro.getLotNumber()[x].trim() %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">Formula:</td>
                     <td class="td04120102">
                        <%=generalInfo[20 + (howManyDetail * 10) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
Enter the Tree Top spec number of the product you are sampling.
Example BAM01, TTA01.">
              Spec Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <input size="5" type="text" maxlength="5" name="<%=y%>spec"
                                                                    value="<%=sro.getSpecNumber()[x].trim()%>">
                     </td>
                  </tr>
<%  // end detail lines
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
                   &nbsp;<img src="<%= expandimg3 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(3,5);">
                   Comments/Remarks
            </font>
            <span <%= expand3 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr05">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0014000005" colspan="2">Comments/Internal Communication:</td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%  // comment lines
   y = 0;
   for(int x = 0; x < 4; x++)
   {
      y = y +1;
%>
                  <input type="hidden" name="<%=y%>commentDate" value="<%= sro.getCommentDate()[x] %>">
                  <input type="hidden" name="<%=y%>commentTime" value="<%= sro.getCommentTime()[x] %>">

                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        <input size="60" type="text" maxlength="60" name="<%=y%>comment"
                                                                    value="<%=sro.getComment()[x].trim()%>">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
   }
%>

                  <tr class="tr05">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0014000005" colspan="2">Remarks(Everyone can see):</td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%  // remark lines
   y = 0;
   for(int x = 0; x < 6; x++)
   {
      y = y + 1;
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        <input size="60" type="text" maxlength="60" name="<%=y%>remark"
                                                                     value="<%=sro.getRemark()[x].trim()%>">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
   }
%>

               </table>
            </span>
         </td>
      </tr>
<%= JavascriptInfo.getCalendarFoot("sampleInfo",
                                         "shipDateInfo",
                                         "shipDate") %>        
<%
}
//******************************************************************************************************
//******************************************************************************************************
  // THIS TEMPLATE IS FOR SHIPPED OR COMPLETE STATUS.
//******************************************************************************************************
//******************************************************************************************************
if (sro.getStatus().trim().equals("SH") || sro.getStatus().trim().equals("CO"))
{
%>
      <tr>
         <td style="width:5%" rowspan="5"></td>
         <td class="td04120102" style="width:20%">
            <acronym title="This number is generated automatically each time a new sample request is started.
You can Refer to this number when you need to locate your sample request in the system.">
               <b>Sample Request Number:</b>
            </acronym>
         </td>
         <td class="td04120102" style="width:20%"> 
           <a class="a0412" href="/web/CtlSampleRequest?requestType=detail&sampleNumber=<%= sro.getSampleNumber() %>" target="_blank"><%= sro.getSampleNumber() %></a>
        <input type="hidden" name="sampleNumber" value="<%= sro.getSampleNumber() %>">
         </td>
         <td class="td04120102">
            <acronym title="Samples have 4 status options:
      Open is for initial entry by the Sales Group
      Pending shows that it is ready to be filled by R&D
      Shipped shows that it has been shipped
               and email confirmations have been sent
      Complete">
               <b>Sample Status:</b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // status drop down list  %>
           <%=generalInfo[9]%>
         </td>
         <td class="td04120102" sytle="text-align:right" colspan="2" >
            <input type="Submit" value="Save Changes">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="To be completed by technicians.
This drop down categorizes the sample by type of product
  and a general description of how it is to be completed.">
               <b>Sample Type:</b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // sample type Hidden Input  %>
           <%= generalInfo[0] %>
         </td>
         <td class="td04120102">
            <acronym title="Must be completed by Entry person.
Shows the Sales rep that is responsible for the requesting customer.">
               <b>Sales Representative:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="2">
<%         // sales rep drop down list  %>
            <%= generalInfo[1] %>
            <input type="hidden" name="sales" value="<%= sro.getSalesRep() %>">
         </td>
         <td class="td04120102" style="width:5%">&nbsp;</td>
      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="To be completed by person entering sample.
ALWAYS ask the requestor how is the sample going to be used..
    What is the customer making?">
               <b><%//Application Type:%></b>
            </acronym>
         </td>
         <td class="td04120102">
<%         // application type drop down list  
//** Hide - requested Removal 9/29/05 TW Project 7142
%>
           <%// generalInfo[2] %>
<%//           <input type="hidden" name="appType" value=" sro.getApplication() "%>
         </td>
         <td class="td04120102">
           <acronym title="Enter the name of the person who requested the sample.">
               <b>Sample Requested By:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="3">
                        <%=sro.getWhoRequested()%>
                        <input type="hidden" name="requestedBy" value="<%=sro.getWhoRequested()%>">
         </td>
      </tr>
       <tr>
         <td class="td04120102">
       <acronym title="Automatically filled in with the date and time you started the sample request.">
               <b>Sample Received Date and Time:</b>
            </acronym>
         </td>
         <td class="td04120102">
            <%=sro.getReceivedDate()%>&nbsp;-&nbsp;<%=sro.getReceivedTime()%>
            <input type="hidden" name="receivedDate" value="<%=sro.getReceivedDate()%>">
            <input type="hidden" name="receivedTime" value="<%=sro.getReceivedTime()%>">
          </td>
         <td class="td04120102">
            <acronym title="Select the name of the person who received and entered this sample request.">
               <b>Sample Request Received By:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="3">
<%                     // sample request received by  %>
                       <%=generalInfo[4] %>
         </td>

      </tr>
      <tr>
         <td class="td04120102">
            <acronym title="Enter the date when you want the sample delivered to the customer.
This will default 10 days after the received date.
This is used by the technicians to determine the best way and when to ship the sample.">
               <b>Sample Delivery Date:</b>
            </acronym>
         </td>
         <td class="td04120102">
            <%=sro.getDeliveryDate()%>
            <input type="hidden" name="deliveryDate" value="<%=sro.getDeliveryDate()%>">
          </td>
         <td class="td04120102">
            <acronym title="Enter the 3 digit number for the broker who is responsible for managing the requesting customer.">
               <b>Broker Territory:</b>
            </acronym>
         </td>
         <td class="td04120102" colspan="2">
            <%=sro.getTerritory()%>
            <input type="hidden" name="territory" value="<%=sro.getTerritory()%>">
         </td>
         <td class="td04120102" style="text-align:center">
            <div>
               &nbsp;&nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Print"
                            onClick="viewmenu(document.all[this.sourceIndex+1]);">
            </div>
            <span class="spanstyle" style="display:none" style=&{head1}; >
               &nbsp;<a href="<%= printPage %>&sampleNumber=<%=sro.getSampleNumber()%>" target="_blank" >Print Sample Request</a><br>
               &nbsp;<a href="<%= printPage %>remarks&sampleNumber=<%=sro.getSampleNumber()%>" target="_blank" >Print Sample Request with Remarks</a><br>
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
                   &nbsp;<img src="<%= expandimg1 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(1,3);">
                   Shipping Information
            </font>
            <span <%= expand1 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:3%" rowspan="25">&nbsp;</td>
                     <td class="td04120102" style="width:25%">Customer Number:</td>
                     <td class="td04120102" style="width:30%">
                        <%= custClass.getCustNumber() %>
                        <input type="hidden" name="custNumber" value="<%= sro.getCustNumber() %>">
                     </td>
                     <td class="td04120102" style="width:25%"><b>SHIP TO:</b></td>
                     <td class="td04120102"><%= custClass.getName() %>&nbsp;</td>
                     <td style="width:3%" rowspan="25">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Use the name pulled from the customer database or enter a new Customer contact in this field.
If you enter a name here it is saved with the sample request only.
It will not update the sample customer master.">
               Customer Contact:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getCustContact()%>
                        <input type="hidden" name="customerContact" value="<%=sro.getCustContact()%>">
                     </td>
                     <td class="td04120102">Customer Address 1:</td>
                     <td class="td04120102"><%= custClass.getAddress1() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title=" Use the phone number pulled from the customer database or enter a new phone number in this field.
If you enter a number here it is saved with the sample request only.
It will not update the sample customer master.">
              Customer Contact Phone:
            </acronym>
                   </td>
                     <td class="td04120102">
                        <%=sro.getCustContactPhone()%>
                        <input type="hidden" name="contactPhone" value="<%=sro.getCustContactPhone()%>">
                     </td>
                     <td class="td04120102">Customer Address 2:</td>
                     <td class="td04120102"><%= custClass.getAddress2() %>&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Use the email address pulled from the customer database or enter a new email address in this field.  If you enter an address here it is saved with the sample request only.
It will not update the sample customer master.
At this point, this is informational only.
Emails are not currently being sent to this address automatically.
If you want to send an email to this address you must enter it into the Additional emails fields.">
              Customer Contact E-Mail:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getCustContactEmail()%>
                        <input type="hidden" name="contactEmail" value="<%=sro.getCustContactEmail()%>">
                     </td>
                     <td class="td04120102">Customer City and State:</td>
                     <td class="td04120102"><%= custClass.getCity() %>&nbsp;<%= custClass.getState() %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter who you want the sample Marked for.
This will be printed on the packing slip of the sample and delivered to this person.">
              Mark To (Attn) Contact (if different):
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAttnContact()%>
                        <input type="hidden" name="attnContact" value="<%=sro.getAttnContact()%>">
                     </td>
<%
   String zipCode = "";
   String zipExtention = "";
   if (custClass.getZip() != 0)
      zipCode      = "" + custClass.getZip();
   if (zipCode.length() == 4)
      zipCode      = "0" + zipCode;
   if (zipCode.length() == 3)
      zipCode      = "00" + zipCode;
   if (zipCode.length() == 2)
      zipCode      = "000" + zipCode;
   if (zipCode.length() == 1)
      zipCode      = "0000" + zipCode;
   if (custClass.getZipExtention() != 0)
      zipExtention = " - " + custClass.getZipExtention();
%>
                     <td class="td04120102">Customer Zip Code and Extension:</td>
                     <td class="td04120102"><%= zipCode %>&nbsp;<%= zipExtention %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the phone number of the Mark to person.
This will print on the packing slip.">
              Mark To Contact Phone:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAttnContactPhone()%>
                        <input type="hidden" name="attnPhone" value="<%=sro.getAttnContactPhone()%>">
                     </td>
                     <td class="td04120102">Customer Postal Code and Country:</td>
                     <td class="td04120102"><%= custClass.getForeignPostalCode() %>&nbsp;<%= custClass.getForeignCountry() %></td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the email address for the Mark to person.
At this point, this is informational only.
Emails are not currently being sent to this address automatically.
If you want to send an email to this address you must enter it into the Additional emails fields.">
              Mark To Contact E-Mail:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAttnContactEmail()%>
                        <input type="hidden" name="attnEmail" value="<%=sro.getAttnContactEmail()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>

                  <tr class="tr00">
                     <td class="td04120102">
    <acronym title="To be entered by the technician when the sample has been shipped and an estimated charges is known.">
              Shipping Charge:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getShippingCharge()%>
                        <input type="hidden" name="shipCharge" value="<%=sro.getShippingCharge()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Ship Via:</td>
                     <td class="td04120102">
<%                     // ship via  %>
                       <%=generalInfo[5] %>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Ship How:</td>
                     <td class="td04120102">
<%                     // ship how  %>
                       <%=generalInfo[6] %>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Tracking Number:</td>
                     <td class="td04120102">
                        <%=sro.getTrackingNumber()%>
                        <input type="hidden" name="trackingNumber" value="<%=sro.getTrackingNumber()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">G/L Account Number:</td>
                     <td class="td04120102">
                        <%=sro.getGlAccountNumber()%>
                        <input type="hidden" name="acctNumber" value="<%=sro.getGlAccountNumber()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
            <acronym title="Enter the 5 digit number.  This number is the 2 digit product line number of the product being sampled combined with the 3 digit broker number.  Ex. 20155 ">
              G/L Account Miscellaneous:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getGlAccountMisc()%>
                        <input type="hidden" name="acctMisc" value="<%=sro.getGlAccountMisc()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Shipping Location:</td>
                     <td class="td04120102">
                         <% // location drop down list  %>
                         <%= generalInfo[10] %>
                         <input type="hidden" name="location" value="<%= sro.getLocation() %>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">Shipping Date:</td>
                     <td class="td04120102">
                        <%=sro.getShippingDate()%>
                        <input type="hidden" name="shipDate" value="<%=sro.getShippingDate()%>">
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">
                     <%//Technician:
                     //** Hide - requested Removal 9/29/05 TW Project 7142
                     %>
                     </td>
                     <td class="td04120102">
                        <%// generalInfo[3] %>
                     </td>
                     <td class="td04120102">&nbsp;</td>
                     <td class="td04120102">&nbsp;</td>
                  </tr>
<%
//** Hide - requested Removal 9/29/05 TW Project 7142
if (0 == 1)
{
%>                
                  <tr class="tr00">
                     <td class="td04120102">
           <acronym title="Click on the documents that you would like to have shipped with this sample request.
You can choose more than one.">
              Documents:
            </acronym>
                     </td>
                     <td class="td04120102" colspan="3">
                        <%// generalInfo[7] %>
                     </td>
                  </tr>
<%
}
%>
                  <tr class="tr00">
                     <td class="td04120102" rowspan="5">
           <acronym title="Enter any additional email address of the people who you want to receive and notification that the sample has been shipped.
This email will include details of the request.
At this time the Sales representative, Who entered the sample and the shipping technician receive the emails automatically. ">
              Additional E-Mail When Shipped:
            </acronym>
                     </td>
                     <td class="td04120102" colspan="3">
                        <%=sro.getEmailWhenShipped1()%>
                        <input type="hidden" name="addEmail1" value="<%=sro.getEmailWhenShipped1()%>">
                     </td>
                  </tr>
<%
   if (sro.getEmailWhenShipped2().trim().length() != 0)
   {
%>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <%=sro.getEmailWhenShipped2()%>
                        <input type="hidden" name="addEmail2" value="<%=sro.getEmailWhenShipped2()%>">
                     </td>
                  </tr>
<%
   }
   if (sro.getEmailWhenShipped3().trim().length() != 0)
   {
%>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <%=sro.getEmailWhenShipped3()%>
                        <input type="hidden" name="addEmail3" value="<%=sro.getEmailWhenShipped3()%>">
                     </td>
                  </tr>
<%
   }
   if (sro.getEmailWhenShipped4().trim().length() != 0)
   {
%>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <%=sro.getEmailWhenShipped4()%>
                        <input type="hidden" name="addEmail4" value="<%=sro.getEmailWhenShipped4()%>">
                     </td>
                  </tr>
<%
   }
   if (sro.getEmailWhenShipped5().trim().length() != 0)
   {
%>
                  <tr class="tr00">
                     <td class="td04120102" colspan="3">
                        <input type="hidden" name="addEmail5" value="<%=sro.getEmailWhenShipped5()%>">
                     </td>
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
                   &nbsp;<img src="<%= expandimg2 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(2,4);">
                   Line Items
            </font>
            <span <%= expand2 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:4%">
                     </td>
                     <td class="td04120102" colspan="4">
<%
   String viewLotChecked = "";
   if (sro.getViewLot() != null &&
       sro.getViewLot().equals("Y"))
     viewLotChecked = "checked";
%>
                        <input type="checkbox" name="viewLot" disabled <%= viewLotChecked %>>&nbsp; Check if you want the Lot Numbers to be seen when Printed and E-mailed.
                     </td>
                     <td style="width:4%">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td>
                     </td>
                     <td class="td04120102" colspan="4">
<%
   String viewVarietyChecked = "";
   if (sro.getViewVariety() != null &&
       sro.getViewVariety().equals("Y"))
     viewVarietyChecked = "checked";
%>
                        <input type="checkbox" name="viewVariety" disabled <%= viewVarietyChecked %>>&nbsp; Check if you want the Shipped Fruit Variety to be seen when Printed and E-mailed.
                     </td>
                     <td>
                     </td>
                  </tr>
<% //detail lines

   int y = 0;
   for(int x = 0 ; x < howManyDetail; x++ )
   {
       y = y + 1;
%>
                  <tr class="tr05">
                     <td style="width:4%">
                         <input type="hidden" name="<%=y%>sequence" value="<%= sro.getDtlSeqNumber()[x] %>">
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="How many of this particular line item would you liked shipped?">
              Quantity
            </acronym>
                     </td>
                     <td class="td0014000005" style="text-align:center">
           <acronym title="Enter the number of the size of the sample.">
              Container Size
            </acronym>
                     </td>
                     <td class="td0014000005">
           <acronym title="Select the measurement you want the technicians to use when filling the sample.">
              Unit of Measure
            </acronym>
                     </td>
                     <td style="width:4%"></td>
                  </tr>

                  <tr class="tr00">
                     <td class="td04140102" rowspan="17">&nbsp;</td>
                     <td class="td04140102" style="text-align:center">
                        <%=quantity[x]%>
                        <input type="hidden" name="<%=y%>quantity" value="<%=quantity[x]%>">
                     </td>
                     <td class="td04140102" style="text-align:center">
                        <%=sro.getContainerSize()[x]%>
                        <input type="hidden" name="<%=y%>containerSize" value="<%=sro.getContainerSize()[x]%>">
                     </td>
                     <td class="td04120102">
<%                     // unit of measure  %>
                       <%=generalInfo[20 + x] %>
                     </td>
                     <td class="td04120102" rowspan="17">&nbsp;</td>
                 </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items being sampled.
You must select the description that best categorizes the item you are sampling.
This option represents the different product lines we sell.
This is used for reporting samples by group.">
              Product Group:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // product Group  %>
                       <%=generalInfo[20 + howManyDetail + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items.
This will make the descriptions that are printed on the sample requests more clear.">
              Fruit Type:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // fruit type  %>
                       <%=generalInfo[20 + (howManyDetail * 7) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items being sampled.
You must select the description that best describes how the product is processed.
If an option is not available then contact the help desk.  ">
              Product Type:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // product Type  %>
                       <%=generalInfo[20 + (howManyDetail * 2) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required for non concentrate items.
Find the best option for the size of the product you are sampling ">
              Cut Size:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // cut size  %>
                       <%=generalInfo[20 + (howManyDetail * 3) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for all items where preservatives might be used.
Select the preservative that will be used for this sample.">
              Additive:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // chemical **word chemical taken out 1/12/07 tw**  additive  %>
                       <%=generalInfo[20 + (howManyDetail * 6) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required if color is different than standard.
Select the option which best describes the color the sample should be.">
              Color:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // color  %>
                       <%=generalInfo[20 + (howManyDetail * 4) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Only required if flavor is different than standard .
Select the option which best describes what the flavor of the product should be">
              Flavor:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // flavor  %>
                       <%=generalInfo[20 + (howManyDetail * 5) + x] %>
                     </td>
                  </tr>

                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required unless customer specifically requests.
Completed by the person entering the sample request.
If the customer requests a specific variety then select it here.
If not then you may leave this field blank.">
              Fruit Variety:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // fruit variety  %>
                       <%=generalInfo[20 + (howManyDetail * 8) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Required for shipping technician if known.
Completed by the technician.
Technician selects the variety of fruit used to fill the sample.
Not required on concentrate samples.">
              Shipped Fruit Variety:
            </acronym>
                     </td>
                     <td class="td04120102">
<%                     // shipped fruit variety  %>
                       <%=generalInfo[20 + (howManyDetail * 9) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required, if you used the fields above to identify the product you want sampled.
If the item is a non stock item, enter your description for what you want sampled here.
Other wise leave this field blank.">
              Item Description:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getItemDescription()[x]%>
                        <input type="hidden" name="<%=y%>itemDescription" value="<%=sro.getItemDescription()[x]%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
If the item is a non stock item, enter more information about the product you would like sampled here.
Other wise leave this field blank.">
              Additional Description:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getAdditionalDescription()[x]%>
                        <input type="hidden" name="<%=y%>addDescription" value="<%=sro.getAdditionalDescription()[x]%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
IF you know the standard resource number of the item you are sampling enter it here.
The resource description will be automatically entered into the Item Description field.">
              Resource Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getResource()[x]%>
                        <input type="hidden" name="<%=y%>resource" value="<%=sro.getResource()[x]%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.  Concentrate only.
If a brix is requested that is different than our standard items.
Enter the requested brix level here.">
              Brix Level:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getBrixLevel()[x]%>
                        <input type="hidden" maxlength="15" name="<%=y%>brixLevel"
                                                                    value="<%=sro.getBrixLevel()[x]%>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
Enter the lot number of the product you want sampled.
This is usually for pre-shipment samples of concentrate.">
              Lot Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%= sro.getLotNumber()[x] %>
                        <input type="hidden" name="<%=y%>lotNumber" value="<%= sro.getLotNumber()[x] %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">Formula:</td>
                     <td class="td04120102">
                        <%=generalInfo[20 + (howManyDetail * 10) + x] %>
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102" colspan="2">
           <acronym title="Not required.
Enter the Tree Top spec number of the product you are sampling.
Example BAM01, TTA01.">
              Spec Number:
            </acronym>
                     </td>
                     <td class="td04120102">
                        <%=sro.getSpecNumber()[x]%>
                        <input type="hidden" name="<%=y%>spec" value="<%=sro.getSpecNumber()[x]%>">
                     </td>
                  </tr>
<%  // end detail lines
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
                   &nbsp;<img src="<%= expandimg3 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(3,5);">
                   Comments/Remarks
            </font>
            <span <%= expand3 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr05">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0014000005" colspan="2">Comments/Internal Communication:</td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%  // comment lines
   y = 0;
   for(int x = 0; x < 4; x++)
   {
      y = y +1;
      if (sro.getComment()[x].trim().length() != 0)
      {
%>
                  <input type="hidden" name="<%=y%>commentDate" value="<%= sro.getCommentDate()[x] %>">
                  <input type="hidden" name="<%=y%>commentTime" value="<%= sro.getCommentTime()[x] %>">
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        <%=sro.getComment()[x]%>
                        <input type="hidden" name="<%=y%>comment" value="<%=sro.getComment()[x]%>">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
      }
      else
      {
%>
                  <input type="hidden" name="<%=y%>commentDate" value="<%= sro.getCommentDate()[x] %>">
                  <input type="hidden" name="<%=y%>commentTime" value="<%= sro.getCommentTime()[x] %>">
                  <input type="hidden" name="<%=y%>comment" value="<%=sro.getComment()[x]%>">
<%
         if (x == 0)
         {
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        &nbsp;
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
         }
      }
   }
%>

                  <tr class="tr05">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0014000005" colspan="2">Remarks(Everyone can see):</td>
                     <td style="width:4%">&nbsp;</td>
                  </tr>
<%  // remark lines
   y = 0;
   for(int x = 0; x < 6; x++)
   {
      y = y + 1;
      if (sro.getRemark()[x].trim().length() != 0)
      {
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        <%=sro.getRemark()[x]%>
                        <input type="hidden" name="<%=y%>remark" value="<%=sro.getRemark()[x]%>">
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
      }
      else
      {
%>
                  <input type="hidden" name="<%=y%>comment" value="<%=sro.getRemark()[x]%>">
<%
         if (x == 0)
         {
%>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td04120102" colspan="2">
                        &nbsp;
                     </td>
                     <td>&nbsp;</td>
                  </tr>
<%
         }
      }
   }
%>
               </table>
            </span>
         </td>
      </tr>
<%
}
%>
      <tr class="tr00">
         <td class="td04120102" style="text-align:center" colspan="3">
            <input type="Submit" value="Save Changes">
         </td>
      </tr>
<%
 // imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
//  imageCount++;
//  expandCount++;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", sro.getListComments());  
  //request.setAttribute("imageCount", (imageCount + ""));  
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Comments", 0, 1, 6, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../../APP/Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>          
<%
 // imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
//  imageCount++;
//  expandCount++;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", sro.getListURLs());  
  //request.setAttribute("imageCount", (imageCount + ""));  
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Attach Documents", 0, 2, 7, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../../APP/Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>               
      
         </form>
    </table>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>