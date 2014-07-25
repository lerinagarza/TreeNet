<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%
//---------------- inqRandDFormula.jsp ---------------------------------------//
//
//  Author :  Teri Walton  5/27/03                                      
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
   request.setAttribute("title", "Search Formula's");
   String parameterList = "&returnToPage=TreeNetInq";
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

//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String formulaNumber          = request.getParameter("formulaNumber");
       if (formulaNumber == null)
         formulaNumber = "";
   String fromFormula            = request.getParameter("fromFormula");
       if (fromFormula == null)
         fromFormula = "";
   String toFormula              = request.getParameter("toFormula");
       if (toFormula == null)
         toFormula = "";
   String fromCreateDate         = request.getParameter("fromCreateDate");
       if (fromCreateDate == null)
         fromCreateDate = "";
   String toCreateDate           = request.getParameter("toCreateDate");
       if (toCreateDate == null)
         toCreateDate = "";
   String fromReviseDate         = request.getParameter("fromReviseDate");
       if (fromReviseDate == null)
         fromReviseDate = "";
   String toReviseDate           = request.getParameter("toReviseDate");
       if (toReviseDate == null)
         toReviseDate = "";
   String formulaName            = request.getParameter("formulaName");
       if (formulaName == null)
         formulaName = "";
   String resource               = request.getParameter("resource");
       if (resource == null)
         resource = "";
   String ingredientDescription  = request.getParameter("ingredientDescription");
       if (ingredientDescription == null)
         ingredientDescription = "";
   String supplier               = request.getParameter("supplier");
       if (supplier == null)
         supplier = "";
   String variety                = request.getParameter("variety");
       if (variety == null)
         variety = "";
   String preservative           = request.getParameter("preservative");
       if (preservative == null)
         preservative = "";
   String tech                   = request.getParameter("tech");
       if (tech == null)
         tech = "";
   String comment                = request.getParameter("comment");
       if (comment == null)
         comment = "";
   String customerName           = request.getParameter("customerName");
       if (customerName == null)
         customerName = "";
%>

<html>
  <head>
    <title>Formula Inquiry</title>
  </head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
 <form action="/web/CtlRandDFormula?requestType=list" method="post">
   <table class="table00" cellspacing="0">
      <tr class="tr02">
         <td style="width:5%">&nbsp;</td>
         <td class="td0418" style="width:23%"><b>&nbsp;</b></td>
         <td class="td0518" style="width:12%"><b>- OR -</b></td>
         <td class="td0418" style="width:15%"><b>&nbsp;</b></td>
         <td class="td0418" style="width:40%"><b>Make Selections and then press</b></td>
         <td class="td0418" style="width:5%"><input type="Submit" value="Go"></td>
      </tr>
   </table>
   <table class="table00" cellspacing="0">
       <tr>
         <td class="td05140102" colspan="2"><b>Choose:</b></td>
         <td class="td04101205" rowspan="12">&nbsp;</td>
         <td class="td05140102" style="width:25%"><b>Choose:</b></td>
         <td class="td05140102" style="width:20%"><b>FROM</b></td>
         <td class="td05140102"><b>TO</b></td>
      </tr>
      <tr>
         <td class="td04120102" style="width:15%"><b>Choose a Formula:</b></td>
         <td class="td04120102" style="width:15%">
            <input size="14" type="text" maxlength="10" name="formulaNumber" value="<%= formulaNumber %>">
         </td>
         <td class="td04120102">
            <b><acronym title="Select Range of Formula's">Formula Numbers:</acronym></b>
         </td>
         <td class="td04120102">
            <input size="10" type="text" maxlength="10" name="fromFormula" value="<%= fromFormula %>">
         </td>
         <td class="td04120102">
            <input size="10" type="text" maxlength="10" name="toFormula" value="<%= toFormula %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102" colspan="2" rowspan="19"></td>
         <td class="td04120102">
            <b><acronym title="Select Range of Date's">Creation Date:</acronym></b>
         </td>
         <td class="td04120102">
            <input size="10" type="text" maxlength="10" name="fromCreateDate" value="<%= fromCreateDate %>">
         </td>
         <td class="td04120102">
            <input size="10" type="text" maxlength="10" name="toCreateDate" value="<%= toCreateDate %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Select Range of Date's">Revision Date:</acronym></b>
         </td>
         <td class="td04120102">
            <input size="10" type="text" maxlength="10" name="fromReviseDate" value="<%= fromReviseDate %>">
         </td>
         <td class="td04120102">
            <input size="10" type="text" maxlength="10" name="toReviseDate" value="<%= toReviseDate %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search within the Formula Name">Formula Name:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <input size="30" type="text" maxlength="30" name="formulaName" value="<%= formulaName %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search by Item Number">Item Number:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <input size="15" type="text" maxlength="15" name="resource" value="<%= resource %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search within the Ingredient Description">Ingredient Description:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <input size="30" type="text" maxlength="30" name="ingredientDescription" value="<%= ingredientDescription %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search by Supplier">Supplier:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <input size="30" type="text" maxlength="30" name="supplier" value="<%= supplier %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search by Variety">Variety:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <select name="variety">
<%
   if (variety.equals(""))
   {
%>
               <option value="">*all
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
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search by Preservative">Preservative:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <input size="30" type="text" maxlength="30" name="preservative" value="<%= preservative %>">
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search by Technician">Technician:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <%= dropDownTech %>
         </td>
      </tr>
      <tr>
         <td class="td04120102">
            <b><acronym title="Search within the Comment Section">Comment Section:</acronym></b>
         </td>
         <td class="td04120102" colspan="2">
            <input size="30" type="text" maxlength="30" name="comment" value="<%= comment %>">
         </td>
      </tr>
<%
//      
//<!--- NOTE***   Take this out until the rest has been tested.
//            No need to hurry, the Customer is not attached to a Formula yet.
//      <tr>
//         <td class="td04120102">
//            Customer:
//         </td>
//         <td class="td04120102" colspan="2">
//            <input size="30" type="text" maxlength="30" name="customerName" value="<%= customerName ">
//            Search by Customer Name.
//         </td>
//      </tr>
//---->
%>        
   </table>
 </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>
