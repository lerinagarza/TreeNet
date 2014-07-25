<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- listRandDFormula.jsp ---------------------------------------//
//
//  Author :  Teri Walton  6/19/03                                      
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
   request.setAttribute("title", "List of Selected Formula's");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlRandDFormula?requestType=add\">Add a New Formula");

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
   String technician             = request.getParameter("technician");
       if (technician == null)
         technician = "";
   String comment                = request.getParameter("comment");
       if (comment == null)
         comment = "";
   String customerName           = request.getParameter("customerName");
       if (customerName == null)
         customerName = "";


//*************************************************
//  Order by can be
//     NumberA      = Formula Number Ascending
//     NumberD      = Formula Number Descending
//     NameA        = Formula Name Ascending (Default if Blank)
//     NameD        = Formula Name Descending
//     CreateDateA  = Creation Date Ascending
//     CreateDateD  = Creation Date Descending
//     ReviseDateA  = Revision Date Ascending
//     ReviseDateD  = Revision Date Descending
//*************************************************
   String sortImg1 = "https://image.treetop.com/webapp/null.gif";
   String sortCol1 = "NumberA";
   String sortImg2 = "https://image.treetop.com/webapp/null.gif";
   String sortCol2 = "NameA";
   String sortImg3 = "https://image.treetop.com/webapp/null.gif";
   String sortCol3 = "CreateDateA";
   String sortImg4 = "https://image.treetop.com/webapp/null.gif";
   String sortCol4 = "ReviseDateA";
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
    if (orderby.equals("CreateDateA"))
    {
       sortImg3 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol3 = "CreateDateD";
    }
    if (orderby.equals("CreateDateD"))
    {
       sortImg3 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
    if (orderby.equals("ReviseDateA"))
    {
       sortImg4 = "https://image.treetop.com/webapp/UpArrowYellow.gif";
       sortCol4 = "ReviseDateD";
    }
    if (orderby.equals("ReviseDateD"))
    {
       sortImg4 = "https://image.treetop.com/webapp/DownArrowYellow.gif";
    }
//*** Use this variable to resend the Parameters for use with Order By
   String parmResend = "&formulaNumber="         + formulaNumber +
                       "&fromFormula="           + fromFormula +
                       "&toFormula="             + toFormula +
                       "&fromCreateDate="        + fromCreateDate +
                       "&toCreateDate="          + toCreateDate +
                       "&fromReviseDate="        + fromReviseDate +
                       "&toReviseDate="          + toReviseDate +
                       "&formulaName="           + formulaName +
                       "&resource="              + resource +
                       "&ingredientDescription=" + ingredientDescription +
                       "&supplier="              + supplier +
                       "&variety="               + variety +
                       "&preservative="          + preservative +
                       "&technician="            + technician +
                       "&comment="               + comment +
                       "&customerName="          + customerName +
                       "&orderby="               + orderby;

//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

//*** Vector of Formula's
   int FormulaCount = 0;
   Vector ListFormulas = new Vector();

try
{
   ListFormulas = (Vector) request.getAttribute("listFormulas");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Formula Vector) = " + e);
}
try
{
   FormulaCount = ListFormulas.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Formula Vector) = " + e);
}
String confirmBoxJavascript = "";
try
{
   confirmBoxJavascript = JavascriptInfo.getConfirmAlertBox("deleteFormula",
		                          "Click OK to Delete this Formula OR Cancel to return");
}
catch (Exception e)
{
  // Catch Problem..
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
width:100px;
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
<%= confirmBoxJavascript %>
<title>List from Inquiry R and D Formula</title>

</head> 

<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>


<table class="table00" cellspacing="0">
   <tr>
       <td style="width:2%">
       </td>
       <td class="td0410">Parms Queried: <%= generalInfo[0] %>
       </td>
       <td style="width:2%">
       </td>
   </tr>
</table>
<table class="table00" cellspacing="0" cellpadding="1">
     <tr class="tr02">
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg1 %>">
          <a class="a0414" href="/web/CtlRandDFormula?requestType=list&orderby=<%= sortCol1 %><%= parmResend %> ">
          Formula Number</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg2 %>">
          <a class="a0414" href="/web/CtlRandDFormula?requestType=list&orderby=<%= sortCol2 %><%= parmResend %> ">
          Formula Name</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg3 %>">
          <a class="a0414" href="/web/CtlRandDFormula?requestType=list&orderby=<%= sortCol3 %><%= parmResend %> ">
          Creation Date and Time</a>
       </td>
       <td class="td04140102" style="text-align:center">
          <img src="<%= sortImg4 %>">
          <a class="a0414" href="/web/CtlRandDFormula?requestType=list&orderby=<%= sortCol4 %><%= parmResend %> ">
          Revision Date and Time</a>
       </td>
       <td style="width:5%">&nbsp;</td>
     </tr>
<%
  for (int x = 0; x < FormulaCount; x++)
   {
      RandDFormula thisrow = (RandDFormula) ListFormulas.elementAt(x);
%>
     <tr class="tr00">
       <td class="td04120102">
          <a class="a0412" href="/web/CtlRandDFormula?requestType=detail&formulaNumber=<%= thisrow.getFormulaNumber() %>" target="_blank"><%= thisrow.getFormulaNumber() %></a>
       </td>
       <td class="td04120102"><%= thisrow.getName() %>
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getCreationDate() %>&nbsp;<%= thisrow.getCreationTime() %>
       </td>
       <td class="td04120102" style="text-align:center"><%= thisrow.getUpdateDate() %>&nbsp;<%= thisrow.getUpdateTime() %>
       </td>
       <td class="td04120102" style="text-align:center">
          <div>
             &nbsp; &nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
             onClick="viewmenu(document.all[this.sourceIndex+1]);">
          </div>
          <span class="spanstyle" style="display:none" style=&{head1}; >
             &nbsp;<a href="/web/CtlRandDFormula?requestType=update&formulaNumber=<%= thisrow.getFormulaNumber() %>" target="_blank">Change Formula</a><br>
             &nbsp;<a href="/web/CtlRandDFormula?requestType=copy&formulaNumber=<%= thisrow.getFormulaNumber() %>" target="_blank">Copy Formula</a><br>
             &nbsp;<a href="JavaScript:deleteFormula('/web/CtlRandDFormula?requestType=delete&deleteFormulaNumber=<%= thisrow.getFormulaNumber() %>&<%= parmResend %>')">Delete Formula</a><br>
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