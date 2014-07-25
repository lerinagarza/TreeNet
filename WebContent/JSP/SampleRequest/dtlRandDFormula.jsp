<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlRandDFormula.jsp ---------------------------------------//
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
   request.setAttribute("title", "Formula Detail");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlRandDFormula?requestType=add\">Add a New Formula");
  
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

//*** Vector of Formula
   Vector formulaDetail = new Vector();
   int    formulaCount  = 0;
try
{
   formulaDetail = (Vector) request.getAttribute("formulaDetail");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Formula Vector) = " + e);
}
try
{
   formulaCount = formulaDetail.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Formula Vector) = " + e);
}

//*** Vector of Customers
   Vector customerList = new Vector();
   int    customerCount  = 0;
try
{
   customerList = (Vector) request.getAttribute("customerList");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Customer List Vector) = " + e);
}
try
{
   customerCount = customerList.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Customer List Vector) = " + e);
}

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

<title>Detail R and D Formula</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
if (formulaCount > 0)
{
      RandDFormula thisrow = (RandDFormula) formulaDetail.elementAt(0);
%>
   <table class="table01" cellspacing="0">
      <tr>
         <td style="width:5%" rowspan="4">
         </td>
         <td class="td04140102" style="width:15%"><b>Formula Number:</b>
         </td>
         <td class="td04140102">
            <%= thisrow.getFormulaNumber() %>&nbsp;
         </td>
         <td class="td04140102"><b>Creation Date/Time:</b>
         </td>
         <td class="td04140102">
            <%= thisrow.getCreationDate() %>&nbsp;<%= thisrow.getCreationTime() %>
         </td>
         <td style="width:5%" rowspan="4">
         </td>
      </tr>
      <tr>
         <td class="td04140102"><b>Formula Name:</b>
         </td>
         <td class="td04140102">
            <%= thisrow.getName() %>&nbsp;
         </td>
         <td class="td04140102"><b>Revision Date/Time:</b>
         </td>
         <td class="td04140102">
            <%= thisrow.getUpdateDate() %>&nbsp;<%= thisrow.getUpdateTime() %>
         </td>
      </tr>
      <tr>
         <td class="td04140102"><b>Variety:</b></td>
         <td class="td04140102">
<%
   String displayVariety = thisrow.getVariety();
   if (displayVariety.equals("FJ"))
      displayVariety = "Fuji";
   if (displayVariety.equals("GL"))
      displayVariety = "Gala";
   if (displayVariety.equals("GD"))
      displayVariety = "Golden Delicious";
   if (displayVariety.equals("GS"))
      displayVariety = "Granny Smith";
   if (displayVariety.equals("JN"))
      displayVariety = "Jonathon";
   if (displayVariety.equals("MX"))
      displayVariety = "Mixed";
   if (displayVariety.equals("OT"))
      displayVariety = "Other";
   if (displayVariety.equals("RD"))
      displayVariety = "Red Delicious";
   if (displayVariety.equals("RM"))
      displayVariety = "Romes";
%>
            <%= displayVariety %>&nbsp;
         </td>
         <td class="td04140102"><b>Technician:</b></td>
         <td class="td04140102"><%= thisrow.getTechnician() %>&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b>Preservative:</b></td>
         <td class="td04140102"><%= thisrow.getPreservative() %>&nbsp;</td>
         <td class="td041401012" colspan = "2">&nbsp;</td>
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
                    <tr class="tr02">
                     <td class="td04120102" style="text-align:center"><b>Supplier</b></td>
                     <td class="td04120102" style="text-align:center"><b>Code</b></td>
                     <td class="td04120102" style="text-align:center"><b>Ingredient</b></td>
                     <td class="td04120102" style="text-align:center"><b>Resource</b></td>
                     <td class="td04120102" style="width:8%; text-align:center"><b>Grams</b></td>
                     <td class="td04120102" style="width:8%; text-align:center"><b>Formula %</b></td>
                     <td class="td04120102" style="width:8%; text-align:center"><b>Dry Weight</b></td>
                     <td class="td04120102" style="width:8%; text-align:center"><b>Weight %</b></td>
                     <td class="td04120102" style="width:8%; text-align:center"><b>Cost / Lb</b></td>
                     <td class="td04120102" style="width:8%; text-align:center"><b>Cost</b></td>
                  </tr>
<%
   BigDecimal totalQuantity       = new BigDecimal("0");
   BigDecimal totalFormulaPercent = new BigDecimal("0");
   BigDecimal totalDryWeight      = new BigDecimal("0");
   BigDecimal totalWeightPercent  = new BigDecimal("0");
   BigDecimal totalCost           = new BigDecimal("0");

   Integer[] seqNum   = thisrow.getSequenceNumber();
   int recordNumber = thisrow.getSequenceNumber().length;
   for (int x = 0; x < recordNumber; x++)
   {
      if (seqNum[x] != null)
      {
         BigDecimal scaleFormulaPercent = new BigDecimal("0.000");
         BigDecimal scaleWeightPercent  = new BigDecimal("0.000");
         try
         {
            scaleFormulaPercent = thisrow.getFormulaPercent()[x].setScale(3,0);
            scaleWeightPercent  = thisrow.getWeightPercent()[x].setScale(3,0);
         }
         catch(Exception e)
         {
         // Not doing anything, if there is a problem.
         }
%>
                  <tr class="tr00">
                     <td class="td04120102">
                        <%= thisrow.getSupplier()[x] %>&nbsp;
                     </td>
                     <td class="td04120102">
                        <%= thisrow.getSupplierCode()[x] %>&nbsp;
                     </td>
                     <td class="td04120102">
                        <%= thisrow.getIngredientDescription()[x] %>&nbsp;
                     </td>
                     <td class="td04120102">
                        <%= thisrow.getResource()[x] %>&nbsp;
                     </td>
                     <td class="td04120102" style="text-align:right">
                        <%= thisrow.getQuantity()[x] %>&nbsp;
                     </td>
                     <td class="td04120102" style="text-align:right">
                        <%= scaleFormulaPercent %>%&nbsp;
                     </td>
                     <td class="td04120102" style="text-align:right">
                        <%= thisrow.getDryWeight()[x] %>&nbsp;
                     </td>
                     <td class="td04120102" style="text-align:right">
                        <%= scaleWeightPercent %>%&nbsp;
                     </td>
                     <td class="td04120102" style="text-align:right">
                        <%= thisrow.getCostPerPound()[x] %>&nbsp;
                     </td>
                     <td class="td04120102" style="text-align:right">
<%
         BigDecimal gramConvert = new BigDecimal("454");
         BigDecimal pounds = thisrow.getQuantity()[x].divide(gramConvert,2);
         BigDecimal CostperIng = pounds.multiply(thisrow.getCostPerPound()[x]);
         CostperIng = CostperIng.setScale(3,0);
%>
                        <%= CostperIng %>
                     </td>
                  </tr>
<%
         totalQuantity       = (totalQuantity.add(thisrow.getQuantity()[x]));
         totalFormulaPercent = totalFormulaPercent.add(thisrow.getFormulaPercent()[x]).setScale(3,0);
         totalDryWeight      = totalDryWeight.add(thisrow.getDryWeight()[x]);
         totalWeightPercent  = totalWeightPercent.add(thisrow.getWeightPercent()[x]).setScale(3,0);
         totalCost           = totalCost.add(CostperIng);
      }
   }
%>
                  <tr class="tr01">
                     <td class="td04120705" colspan="3">&nbsp;</td>
                     <td class="td04140705"><b>TOTAL:</b></td>
                     <td class="td04120705" style="text-align:right">
                        <b><%= totalQuantity %></b>
                     </td>
                     <td class="td04120705" style="text-align:right">
                        <b><%= totalFormulaPercent %>%</b>
                     </td>
                     <td class="td04120705" style="text-align:right">
                        <b><%= totalDryWeight %></b>
                     </td>
                     <td class="td04120705" style="text-align:right">
                        <b><%= totalWeightPercent %>%</b>
                     </td>
                     <td class="td04120705">&nbsp;</td>
                     <td class="td04120705" style="text-align:right">
                        <b><%= totalCost %></b>
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
                   &nbsp;<img src="<%= expandimg3 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(2,4);">
                   Comment Section
            </font>
            <span <%= expand3 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr00">
                     <td style="width:4%">&nbsp;</td>
                     <td class="td0412">
                        <textarea cols="110" rows="20" style="background-color:#F3FAFF; font-family:arial; font-size:10pt; color:#006400; text-align:left" readonly>
                            <%= thisrow.getComment().trim() %>
                        </textarea>
                     </td>
                    <td style="width:4%">&nbsp;</td>
                  </tr>
               </table>
            </span>
         </td>
      </tr>
<%
   if (customerCount > 0)
   {
%>
      <tr class="tr02001">
         <td class="td051CL002">
            <font style="color:#990000;
                         font-weight:bold;
                         font-family:arial;
                         font-size:12pt;
                         text-align:left;">
                   &nbsp;<img src="<%= expandimg3 %>" style="cursor:hand"
                          onClick="doit(document.all[this.sourceIndex+1]);
                          changeImage(3,5);">
                   Customers
            </font>
            <span <%= expand3 %> style=&{head};>
               <table class="table00" cellspacing="0">
                  <tr class="tr02">
                     <td style="width:4%" rowspan="4">&nbsp;</td>
                     <td class="td04140102"><b>Broker Number:</b></td>
                     <td class="td04140102"><b>Broker Name:</b></td>
                     <td class="td04140102"><b>Customer Number:</b></td>
                     <td class="td04140102"><b>Customer Name:</b></td>
                     <td style="width:4%" rowspan="4">&nbsp;</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">110</td>
                     <td class="td04120102">Byron Carlson</td>
                     <td class="td04120102">110008</td>
                     <td class="td04120102">Sara Lee Bakery</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">115</td>
                     <td class="td04120102">Gerhrke Company</td>
                     <td class="td04120102">115008</td>
                     <td class="td04120102">The Pillsbury Company</td>
                  </tr>
                  <tr class="tr00">
                     <td class="td04120102">115</td>
                     <td class="td04120102">Gerhrke Company</td>
                     <td class="td04120102">115032</td>
                     <td class="td04120102">Best Brands</td>
                  </tr>
               </table>
            </span>
         </td>
      </tr>
   </table>
<%
   }
}
else
{
   String msg = "No Detail Record Available";
}
%>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>