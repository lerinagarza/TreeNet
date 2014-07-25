<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>

<%
//---------------- updRandDFormula.jsp ---------------------------------------//
//
//  Author :  Teri Walton  6/20/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/17/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Add/Copy/Update Formula");
   String parameterList = "&returnToPage=CtlRandDFormula";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlRandDFormula?requestType=add\">Add a New Formula");

//**************************************************************************//
// Get Request Session Variables for Display on this Screen
//**************************************************************************
   String dropDownTech           = (String) request.getAttribute("dropdownUser");
       if (dropDownTech == null)
          dropDownTech = "";
   String comment                = (String) request.getAttribute("comment");
       if (comment == null)
         comment = "";
//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String formulaNumber          = request.getParameter("formulaNumber");
       if (formulaNumber == null || formulaNumber.length() == 0)
         formulaNumber = "";
   String formulaType            = request.getParameter("formulaType");
       if (formulaType == null)
         formulaType = "";
   String formulaName            = request.getParameter("formulaName");
       if (formulaName == null)
         formulaName = "";
   String tech                   = request.getParameter("tech");
       if (tech == null)
         tech = "";
   String variety                = request.getParameter("variety");
       if (variety == null)
         variety = "";
   String preservative           = request.getParameter("preservative");
       if (preservative == null)
         preservative = "";
   String creationDate           = request.getParameter("creationDate");
       if (creationDate == null)
         creationDate = "";
   String revisionDate           = request.getParameter("revisionDate");
       if (revisionDate == null)
         revisionDate = "";

  // Default Information to come in open
   String expand1 = "";
   String expandimg1 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand2 = "";
   String expandimg2 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand3 = "";
   String expandimg3 = "https://image.treetop.com/webapp/minusbox3.gif";
   String expand4 = "";
   String expandimg4 = "https://image.treetop.com/webapp/minusbox3.gif";

// Resetting the Request Type for what to do when leaving this page.
   String rdRadioValue = "";
   String prodRadioValue = "";
   String requestType = request.getParameter("requestType");
   if (requestType == null)
      requestType = "add";
   String sendRequestType = "addFinish";
   if (requestType.equals("add"))
   {
      rdRadioValue = "checked";
   }

   if (requestType.equals("copy"))
      sendRequestType = "addFinish";
   if (requestType.equals("update"))
      sendRequestType = "updateFinish";
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

<title>Update/Add R and D Formula</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
   if (requestType.equals("updateFinish") || requestType.equals("addFinish"))
   {
   //**** if this is true, reload the page.
   String msg = request.getParameter("msg");
%>
<script language="JavaScript1.2">
<!--
 
location.replace("CtlRandDFormula?requestType=update&formulaNumber=<%= formulaNumber %>&msg=<%= msg %>");
-->
</script>
<%
   }
%>
<form action="/web/CtlRandDFormula?requestType=<%=sendRequestType%>" method="post">
   <table class="table01" cellspacing="0">
      <tr>
         <td style="width:2%" rowspan="4">&nbsp;</td>
         <td class="td04140102" rowspan="4">
            <b><input type="radio" checked name="formulatype" value="rdFormula">&nbsp;R and D Formula</b>
         </td>
         <td class="td04140102" style="width:12%"><b>Formula Number:</b></td>
         <td class="td04140102">
            <%= formulaNumber %>
            <input type="hidden" name="formulaNumber" value="<%= formulaNumber %>">
         </td>
<%
   if (!requestType.equals("add") && !requestType.equals("copy"))
   {
%>
         <td class="td04140102"><b>Creation Date:</b></td>
         <td class="td04140102"><%= creationDate %>&nbsp;</td>
<%
   }
%>
        <td style="width:2%" rowspan="4"></td>
      </tr>
      <tr>
         <td class="td04140102"><b>Formula Name:</b></td>
         <td class="td04140102">
            <input size="50" type="text" maxlength="50" name="formulaName" value="<%= formulaName %>">
         </td>
         <td class="td04140102"><b>Created By:</b></td>
         <td class="td04140102"><%= dropDownTech %></td>
      </tr>
      <tr>
         <td class="td04140102"><b>Variety:</b></td>
         <td class="td04140102">
            <select name="variety">
<%
   if (variety.equals(""))
   {
%>
               <option value="">Choose a Variety 
<%
   }
   else
   {
%>
               <option value="">No Variety Chosen
<%
   }
   if (variety.equals("FJ"))
   {
%>
               <option value="FJ" selected="selected">Fuji
<%
   }
   else
   {
%>
               <option value="FJ">Fuji
<%
   }
   if (variety.equals("GL"))
   {
%>
               <option value="GL" selected="selected">Gala
<%
   }
   else
   {
%>
               <option value="GL">Gala
<%
   }
   if (variety.equals("GD"))
   {
%>
               <option value="GD" selected="selected">Golden Delicious
<%
   }
   else
   {
%>
               <option value="GD">Golden Delicious
<%
   }
   if (variety.equals("GS"))
   {
%>
               <option value="GS" selected="selected">Granny Smith
<%
   }
   else
   {
%>
               <option value="GS">Granny Smith
<%
   }
   if (variety.equals("JN"))
   {
%>
               <option value="JN" selected="selected">Jonathon
<%
   }
   else
   {
%>
               <option value="JN">Jonathon
<%
   }
   if (variety.equals("MX"))
   {
%>
               <option value="MX" selected="selected">Mixed
<%
   }
   else
   {
%>
               <option value="MX">Mixed
<%
   }
   if (variety.equals("OT"))
   {
%>
               <option value="OT" selected="selected">Other
<%
   }
   else
   {
%>
               <option value="OT">Other
<%
   }
   if (variety.equals("RD"))
   {
%>
               <option value="RD" selected="selected">Red Delicious
<%
   }
   else
   {
%>
               <option value="RD">Red Delicious
<%
   }
   if (variety.equals("RM"))
   {
%>
               <option value="RM" selected="selected">Romes
<%
   }
   else
   {
%>
               <option value="RM">Romes
<%
   }
%>
            </select>
         </td>
<%
   if (!requestType.equals("add") && !requestType.equals("copy"))
   {
%>
         <td class="td04140102"><b>Last Revision Date:</b></td>
         <td class="td04140102"><%= revisionDate %></td>
<%
   }
%>
      </tr>
      <tr>
         <td class="td04140102"><b>Preservative:</b></td>
         <td class="td04140102">
            <input size="20" type="text" maxlength="20" name="preservative" value="<%= preservative %>">
         </td>
         <td class="td04140102" colspan="2">&nbsp;</td>
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
<%
   for(int x = 1; x < 16; x++)
   {
      String seqNumber = x + "";
      String nameInfo  = "" + x;

      String supplierInfo     = x + "supplier";
        String supplier = request.getParameter(supplierInfo);
           if (supplier == null)
              supplier = "";
      String supplierCodeInfo = x + "supplierCode";
        String supplierCode = request.getParameter(supplierCodeInfo);
           if (supplierCode == null)
              supplierCode = "";
      String ingDescInfo      = x + "ingDesc";
        String ingDesc = request.getParameter(ingDescInfo);
           if (ingDesc == null)
              ingDesc = "";
      String resourceInfo     = x + "resource";
        String resource = request.getParameter(resourceInfo);
           if (resource == null)
              resource = "";
      String qtyInfo          = x + "qty";
        String qty = request.getParameter(qtyInfo);
           if (qty == null)
              qty = "";
      String drywgtInfo       = x + "drywgt";
        String drywgt = request.getParameter(drywgtInfo);
           if (drywgt == null)
              drywgt = "";
      String costperlbInfo    = x + "costperlb";
        String costperlb = request.getParameter(costperlbInfo);
           if (costperlb == null)
              costperlb = "";
%>
                  <tr class="tr04">
                     <td colspan="5"></td>
                  </tr>
                  <tr class="tr00">
<%
   if(requestType.equals("update") || requestType.equals("copy"))
   {
%>
                     <td class="td0414" style="text-align:center" rowspan="7">Delete
                        <input type="checkbox" name="<%= nameInfo %>delete">
                     </td>
<%
   }
   else
   {
%>
                     <td class="td0414" rowspan="7">&nbsp;</td>
<%
   }
%>
                     <td class="td0414" style="text-align:center" rowspan="7">Sequence Number
                        <input size="3" type="text" name="<%= nameInfo %>seq" value="<%= seqNumber %>">
                     </td>
                     <td class="td04140102"><b>Supplier:</b></td>
                     <td class="td04140102">
                        <input size="30" type="text" maxlength="30" name="<%= nameInfo %>supplier" value="<%= supplier %>">
                     </td>
                     <td class="td0414" rowspan="7">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Supplier Code:</b></td>
                     <td class="td04140102">
                        <input size="15" type="text" maxlength="15" name="<%= nameInfo %>supplierCode" value="<%= supplierCode %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Ingredient Description:</b></td>
                     <td class="td04140102">
                        <input size="50" type="text" maxlength="50" name="<%= nameInfo %>ingDesc" value="<%= ingDesc %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Resource Number:</b></td>
                     <td class="td04140102">
                        <input size="15" type="text" maxlength="15" name="<%= nameInfo %>resource" value="<%= resource %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Grams:</b>
                        <input type="hidden" name="<%= nameInfo %>uom" value="grams">
                     </td>
                     <td class="td04140102">
                        <input size="13" type="text" maxlength="13" name="<%= nameInfo %>qty" value="<%= qty %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04140102"><b>Dry Weight:</b></td>
                     <td class="td04140102">
                        <input size="13" type="text" maxlength="13" name="<%= nameInfo %>drywgt" value="<%= drywgt %>">
                     </td>
                  </tr>
                  <tr class="tr00">
                     <td class="td0414"><b>Cost Per Pound:</b></td>
                     <td class="td0414">
                        <input size="13" type="text" maxlength="13" name="<%= nameInfo %>costperlb" value="<%= costperlb %>">
                     </td>
                  </tr>
                  <tr class="tr04">
                     <td colspan="5"></td>
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
                   &nbsp;<img src="<%= expandimg3 %>" style="cursor:hand"

                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(2,4);">
                   Comment Section
            </font>
            <span <%= expand3 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr01">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0416">
                        Comments:
                     <td style="width:4%">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td>&nbsp;</td>
                     <td class="td0412">
                        <textarea name="comment" rows="20" cols="80"><%= comment.trim() %>
                           </textarea>
                     <td>&nbsp;</td>
                  </tr>
               </table>
            </span>
         </td>
      </tr>
      <tr class="tr01">
         <td colspan="3" style="text-align:center">
            <input type="Submit" value="Save Changes">
         </td>
      </tr>
   </table>
         </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>