<%@ page language = "java" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.treetop.data.*" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*, java.text.*" %>
<%
//---------------- listFormulaTTSpecs.jsp -------------------------------//
// Author :  Charlena Paschen  10/31/03
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/21/11   TWalton	   Change AS400 access to be without IP address
//  10/13/08   TWalton	   Point to NEW AS400
//  2/23/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------//
//**************************************************************************//
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title","List of Formulas for TT Specs");
   String parameterList = "&returnToPage=/web/TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "");

//*****************************************************************************
//********************************************************************
// Execute security servlet.
//********************************************************************
   // Allow Session Variable Access
//   HttpSession sess = request.getSession(true);
		
   // Set the Status
   SessionVariables.setSessionttiSecStatus(request,response,"");
		
   // Set the URL address used by the security servlet.
   String urlAddress = "/web/JSP/TTSpecs/inqFormulaTTSpecs.jsp";
   SessionVariables.setSessionttiTheURL(request, response, urlAddress);

   // Call the security Servlet
   getServletConfig().getServletContext().
   getRequestDispatcher("/TTISecurity" ).
   include(request, response);

   // Decision of whether or not to use the Inq, List or Detail
   if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
   {
      String x = "Authority was not granted for this page " +
                 "(List CPG Formulas). " +
                 "Please contact the Information Services Help Desk" ;
      response.sendRedirect("/web/TreeNetInq?msg=" + x);
      return;
   }
		
   //remove the Status and the Url
//   sess.removeAttribute("ttiTheURL");
//   sess.removeAttribute("ttiSecStatus");

//*********************************************************************
%>


<%! 
//******************************************************************************
//   Declare Variables
//******************************************************************************
  Connection conn  = null; // AS400

  //   String sys = ("10.6.100.3"); // 10/21/11 twalton - change to NOT use IP address
   String sys = ("lawson.treetop.com");
  String userProfile = ("DAUSER");
  String password    = ("WEB230502");

  String driver      = "com.ibm.as400.access.AS400JDBCDriver";
  
    String formula="";
    String formulaname="";
    String prodstat = "";
    BigDecimal revdate = new BigDecimal("0");
    BigDecimal revdateBD = new BigDecimal("0");
    String recstat = "";
    String selectInfo = "";

    String parmResend = "";
    String sortOrder = "";

    boolean noRecords;

    String sortorderimagea = "https://image.treetop.com/webapp/null.gif";
    String sortorderimageb = "https://image.treetop.com/webapp/null.gif";
    String sortorderimagec = "https://image.treetop.com/webapp/null.gif";
    String sortorderimaged = "https://image.treetop.com/webapp/null.gif";
    String sortorderimagee = "https://image.treetop.com/webapp/null.gif";
    String sortorderimagef = "https://image.treetop.com/webapp/null.gif";
    String sortorderimageg = "https://image.treetop.com/webapp/null.gif";
    String sortorderimageh = "https://image.treetop.com/webapp/null.gif";

    String sortordercol1 = "A";
    String sortordercol2 = "C";
    String sortordercol3 = "E";
    String sortordercol4 = "G";

%>
<%
//**************************************************************************
// Connection test for null
//**************************************************************************
 if (conn == null)
 {

        Driver drv      = (Driver) Class.forName(driver).newInstance();
        Properties prop = new Properties();
        prop.put("user", userProfile);
        prop.put("password", password);

        conn            = DriverManager.getConnection("jdbc:as400:" + sys, prop);
 }
noRecords = true;
String formulaName = request.getParameter("formula");
   if (formulaName == null)
      formulaName = "";
String formulaParmValue = request.getParameter("formula");

String formulanameName = request.getParameter("formulaname");
   if (formulanameName == null)
      formulanameName = "";
String formulanameParmValue = request.getParameter("formulaname");

String revdateName = request.getParameter("revdate");
   if (revdateName == null)
      revdateName = "";
String revdateParmValue = request.getParameter("revdate");

String prodstatName = request.getParameter("title");
   if (prodstatName == null)
      prodstatName = "";
String prodstatParmValue = request.getParameter("title");

String recstatName = request.getParameter("recstat");
   if (recstatName == null)
       recstatName = "";
String recstatParmValue = request.getParameter("recstat");


String sortBy = request.getParameter("orderby");

%>

<%
//********************************************************//
//***  Make sure number parms are numbers               **//
//********************************************************//

     if  (revdateParmValue.length()!=0)
       {
       try {
           revdateBD =  new BigDecimal(revdateParmValue);
           }	
       catch (NumberFormatException nfe)
           {
           revdateBD = new BigDecimal("0");
           }	
       }

   if (sortBy == null) {
       sortBy = "A"; }
%>
<%
  String whereStatement = "WHERE";
  if (!formulaName.equals("")) {
    whereStatement = whereStatement + " SPLFORM LIKE \'%" + formulaName + "%\' ";
    selectInfo = "Formula Number - " + formulaParmValue + " ";
    }

  if (!formulanameName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " UPPER(SPLNAME) LIKE \'%" + formulanameName.toUpperCase() + "%\' ";
       selectInfo = "Formula Name - " + formulanameParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND UPPER(SPLNAME) LIKE \'%" + formulanameName.toUpperCase() + "%\' ";
       selectInfo = selectInfo + " AND Formula Name - " + formulanameParmValue + " ";
       }
  }

  if (!prodstatName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " UPPER(SPLSTATUS) LIKE \'%" + prodstatName.toUpperCase() + "%\' ";
       selectInfo = "Production Status - " + prodstatParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND UPPER(SPLSTATUS) LIKE \'%" + prodstatName.toUpperCase() + "%\' ";
       selectInfo = selectInfo + " AND Production Status - " + prodstatParmValue + " ";
       }
  }

  if (!revdateName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " SPLREVIS = " + revdateName + " ";
       selectInfo = " Revision Date - " + revdateParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND SPLREVIS = " + revdateName + " ";
       selectInfo = selectInfo + " AND Revision Date - " + revdateParmValue + " ";
       }
  }

  if (!recstatName.equals("all") &&
      !recstatName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " SPLRECST LIKE \'" + recstatName + "%\' ";
       selectInfo = " Record Status - " + recstatParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND SPLRECST LIKE \'" + recstatName + "%\' ";
       selectInfo = selectInfo + " AND Record Status - " + recstatParmValue + " ";
       }
  }
  if (whereStatement.equals("WHERE"))
     whereStatement = "";

//*******************************************************************************
// Resending all the Parameters for Sort Order Determination
//*******************************************************************************
   parmResend =  "formula=" + formulaName + "&" +
                 "formulaname=" + formulanameName + "&" +
                 "prodstat=" + prodstatName + "&" +
                 "revdate=" + revdateName + "&" +
                 "recstat=" + recstatName;

//*******************************************************************************
// Determine Sort Order
//*******************************************************************************
   if (request.getParameter("orderby") == null)
         {
          sortBy = "A";
         }

    if (sortBy.equals("A"))
         {
          sortOrder="SPLFORM";
          sortorderimagea="https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol1="B";
         }
     else
         {
          sortorderimagea = "https://image.treetop.com/webapp/null.gif";
         }

       if (sortBy.equals("B"))
         {
          sortOrder="SPLFORM DESC";
          sortorderimageb="https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol1="A";
         }
     else
         {
          sortorderimageb="https://image.treetop.com/webapp/null.gif";
         }
//******************* Column 2 Sort *************************//

    if (sortBy.equals("C"))
         {
          sortOrder="SPLNAME";
          sortorderimagec="https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol2="D";
         }
     else
         {
          sortorderimagec="https://image.treetop.com/webapp/null.gif";
         }

    if (sortBy.equals("D"))
         {
          sortOrder = "SPLNAME DESC";
          sortorderimaged="https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol2="C";
         }
     else
         {
          sortorderimaged="https://image.treetop.com/webapp/null.gif";
         }

//***************** Column 3 sort *****************************//

   if (sortBy.equals("E"))
         {
          sortOrder="SPLREVIS";
          sortorderimagee="https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol3="F";
         }
     else
         {
          sortorderimagee="https://image.treetop.com/webapp/null.gif";
         }

    if (sortBy.equals("F"))
         {
          sortOrder="SPLREVIS DESC";
          sortorderimagef="https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol3="E";
         }
     else
         {
          sortorderimagef="https://image.treetop.com/webapp/null.gif";
         }

//***************** Column 4 sort *****************************//

   if (sortBy.equals("G"))
         {
          sortOrder="SPLSTATUS";
          sortorderimageg="https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol4="H";
         }
     else
         {
          sortorderimageg="https://image.treetop.com/webapp/null.gif";
         }

    if (sortBy.equals("H"))
         {
          sortOrder="SPLSTATUS DESC";
          sortorderimageh="https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol4="G";
         }
     else
         {
          sortorderimageh="https://image.treetop.com/webapp/null.gif";
         }

%>
<html>
 <head>
        <title>List of Formulas for TT Specs</title>
  </head>
<body>
<jsp:include page="../include/heading.jsp"></jsp:include>  

<!--- Start of Table Data ----------------->

<table class="table01001" cellspacing="0">
   <tr>
       <td style="width:2%">
       </td>
       <td class="td044CR001" style="width:15%"><b> Selection Details:&nbsp;&nbsp; </b>
       </td>
       <td class="td064CL001"><b><%= selectInfo %></b>
       </td>
       <td style="width:2%">
       </td>
   </tr>
</table>

<table class="table00001" cellspacing="0" cellpadding="1" border="1">

   <tr class="tr02001">

       <td class="td044CC001"><img src="<%= sortorderimagea %>"><img src="<%= sortorderimageb %>"><a class="a04002" href="listFormulaTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol1 %> ">Formula Number</a>
       </td>
       <td class="td044CC001"><img src="<%= sortorderimagec %>"><img src="<%= sortorderimaged %>"><a class="a04002" href="listFormulaTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol2 %> ">Formula Name</a>
       </td>
       <td class="td044CC001"><img src="<%= sortorderimagee %>"><img src="<%= sortorderimagef %>"><a class="a04002" href="listFormulaTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol3 %> ">Revision Date</a>
       </td>
       <td class="td044CC001"><img src="<%= sortorderimageg %>"><img src="<%= sortorderimageh %>"><a class="a04002" href="listFormulaTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol4 %> ">Production Status</a>
       </td>
       <td class="td044CC001"><b>Record Status</b>
       </td>
       </tr>

<!--- SQL Statement--------------->

<%
    try
     {
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLLFRM1 " +
                                    whereStatement +
                                    " ORDER BY " + sortOrder );
%>

<%

   while (rs.next()) {

       noRecords = false;
%>
       <tr class="tr00001">
       <td class="td041CR002"><a class="a04002" href="../Router/formulaRouter.jsp?formula=<%= rs.getString("SPLFORM")%>" target="_blank"><%= rs.getString("SPLFORM")%></a> &nbsp;
       </td>
       <td class="td041CL002"><%= rs.getString("SPLNAME")%> &nbsp;
       </td>
       <td class="td041CR002"><%= rs.getString("SPLREVIS")%> &nbsp;
       </td>
       <td class="td047CL002"><%= rs.getString("SPLSTATUS")%> &nbsp;
       </td>
       <td class="td047CR002"><%= rs.getString("SPLRECST") %> &nbsp;
       </td>
       </tr>
<% } // end while - start of error message if no records found

     if (noRecords) { %>

     <script language="JavaScript">
     alert("No Such Match Found!")
     </script>

<% }

   rs.close();
   stmt.close();
} catch (SQLException e) {
    out.println("SQL error : " + e);
  }

%>
</table>
<jsp:include page="../include/footer.jsp"></jsp:include>  

</body>
</html>