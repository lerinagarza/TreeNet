<%@ page language = "java" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.treetop.data.*" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*, java.text.*" %>
<%
//---------------- listMethodTTSpecs.jsp --------------------------------------------//
//  Author :  Charlena Paschen  10/30/03                                          

//   Date       Name       Comments
// --------   ---------   -------------
//  10/21/11   TWalton	   Change AS400 access to be without IP address
//  2/23/04    TWalton     Changed comments and images for 5.0 server.                                                                       
//
//-------------------------------------------------------------------------------//
//**************************************************************************//
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title","List of Methods");
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
   HttpSession sess = request.getSession(true);
		
   // Set the Status
   SessionVariables.setSessionttiSecStatus(request,response,"");
		
   // Set the URL address used by the security servlet.
   String urlAddress = "/web/JSP/TTSpecs/inqMethodTTSpecs.jsp";
   SessionVariables.setSessionttiTheURL(request, response, urlAddress);

   // Call the security Servlet
   getServletConfig().getServletContext().
   getRequestDispatcher("/TTISecurity" ).
   include(request, response);

   // Decision of whether or not to use the Inq, List or Detail
   if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
   {
      String x = "Authority was not granted for this page " +
                 "(List Methods). " +
                 "Please contact the Information Services Help Desk" ;
      response.sendRedirect("/web/TreeNetInq?msg=" + x);
      return;
   }
		
   //remove the Status and the Url
   sess.removeAttribute("ttiTheURL");
   sess.removeAttribute("ttiSecStatus");

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
  
    BigDecimal method = new BigDecimal("0.000");
    BigDecimal category = new BigDecimal("0.000");
    BigDecimal methodBD = new BigDecimal("0.000");
    BigDecimal categoryBD = new BigDecimal("0.000");
    String title = "";
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
String methodName = request.getParameter("method");
   if (methodName == null)
      methodName = "";
String methodParmValue = request.getParameter("method");
String categoryName = request.getParameter("category");
   if (categoryName == null)
      categoryName = "";
String categoryParmValue = request.getParameter("category");
String titleName = request.getParameter("title");
   if (titleName == null)
      titleName = "";
String titleParmValue = request.getParameter("title");
String revdateName = request.getParameter("revdate");
   if (revdateName == null)
      revdateName = "";
String revdateParmValue = request.getParameter("revdate");
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
try
{
   if  (methodParmValue.length()!=0)
       {
       try {
           methodBD =  new BigDecimal(methodParmValue);
           }	
       catch (NumberFormatException nfe)
           {
           methodBD = new BigDecimal("0.000");
           }	
       }

     if  (categoryParmValue.length()!=0)
       {
       try {
           categoryBD =  new BigDecimal(categoryParmValue);
           }	
       catch (NumberFormatException nfe)
           {
           categoryBD = new BigDecimal("0.000");
           }	
       }

      if  (categoryParmValue.length()==0)
       {
       try {
           categoryBD =  new BigDecimal(categoryParmValue);
           }	
       catch (NumberFormatException nfe)
           {
           categoryBD = new BigDecimal("0.000");
           }	
       }

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
}
catch(Exception e)
{
   // use this to debug if problems occur, put out.println here
}
   if (sortBy == null) {
       sortBy = "A"; }
%>
<%
  String whereStatement = "WHERE";
  if (!methodName.equals("")) {
    whereStatement = whereStatement + " SPNMETH = " + methodName + " ";
    selectInfo = "Method - " + methodParmValue + " ";
    }

  if (!categoryName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " SPNCAT = " + categoryName + " ";
       selectInfo = "Category - " + categoryParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND SPNCAT = " + categoryName + " ";
       selectInfo = selectInfo + " AND Category - " + categoryParmValue + " ";
       }
  }

  if (!titleName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " UPPER(SPNTITLE) LIKE \'%" + titleName.toUpperCase() + "%\' ";
       selectInfo = "Title - " + titleParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND UPPER(SPNTITLE) LIKE \'%" + titleName.toUpperCase() + "%\' ";
       selectInfo = selectInfo + " AND Title - " + titleParmValue + " ";
       }
  }

  if (!revdateName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " SPNREVIS = " + revdateName + " ";
       selectInfo = " Revision Date - " + revdateParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND SPNREVIS = " + revdateName + " ";
       selectInfo = selectInfo + " AND Revision Date - " + revdateParmValue + " ";
       }
  }

  if (!recstatName.equals("all") &&
      !recstatName.equals(""))
  {
    if (whereStatement.equals("WHERE")) {
       whereStatement = whereStatement + " SPNRECST LIKE \'" + recstatName + "%\' ";
       selectInfo = " Record Status - " + recstatParmValue + " ";
       }
    else {
       whereStatement = whereStatement + " AND SPNRECST LIKE \'" + recstatName + "%\' ";
       selectInfo = selectInfo + " AND Record Status - " + recstatParmValue + " ";
       }
  }
  if (whereStatement.equals("WHERE"))
     whereStatement = "";
//*******************************************************************************
// Resending all the Parameters for Sort Order Determination
//*******************************************************************************
   parmResend =  "method=" + methodName + "&" +
                 "category=" + categoryName + "&" +
                 "title=" + titleName + "&" +
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
          sortOrder = "SPNMETH";
          sortorderimagea = "https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol1 = "B";
         }
     else
         {
          sortorderimagea = "https://image.treetop.com/webapp/null.gif";
         }

     if (sortBy.equals("B"))
         {
          sortOrder = "SPNMETH DESC";
          sortorderimageb = "https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol1 = "A";
         }
     else
         {
          sortorderimageb = "https://image.treetop.com/webapp/null.gif";
         }
//************ Column 2 Sort *********************************//

    if (sortBy.equals("C"))
         {
          sortOrder = "SPNCAT";
          sortorderimagec = "https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol2 = "D";
         }
     else
         {
          sortorderimagec = "https://image.treetop.com/webapp/null.gif";
         }

     if (sortBy.equals("D"))
         {
          sortOrder = "SPNCAT DESC";
          sortorderimaged = "https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol2="C";
         }
     else
         {
          sortorderimaged = "https://image.treetop.com/webapp/null.gif";
         }

//*************** Column 3 sort ****************************//

   if (sortBy.equals("E"))
         {
          sortOrder = "SPNTITLE";
          sortorderimagee = "https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
          sortordercol3="F";
         }
     else
         {
          sortorderimagee = "https://image.treetop.com/webapp/null.gif";
         }

       if (sortBy.equals("F"))
         {
          sortOrder = "SPNTITLE DESC";
          sortorderimagef = "https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
          sortordercol3="E";
         }
     else
         {
          sortorderimagef = "https://image.treetop.com/webapp/null.gif";
         }

//*************** Column 4 sort ************************************//

   if (sortBy.equals("G"))
        {
         sortOrder = "SPNREVIS";
         sortorderimageg = "https://image.treetop.com/webapp/UpArrowGreenTrans.gif";
         sortordercol4="H";
         }
     else
        {
         sortorderimageg = "https://image.treetop.com/webapp/null.gif";
        }

     if (sortBy.equals("H"))
        {
         sortOrder = "SPNREVIS DESC";
         sortorderimageh = "https://image.treetop.com/webapp/DownArrowGreenTrans.gif";
         sortordercol4="G";
         }
     else
        {
         sortorderimageh = "https://image.treetop.com/webapp/null.gif";
        }
%>
<html>
 <head>
 <title>List of Methods</title>
   </head>
<body>
<jsp:include page="../include/heading.jsp"></jsp:include>  

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

       <td class="td044CC001"><img src="<%= sortorderimagea %>"><img src="<%= sortorderimageb %>"><a class="a04002" href="listMethodTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol1 %> ">Method</a>
       </td>
       <td class="td044CC001"><img src="<%= sortorderimagec %>"><img src="<%= sortorderimaged %>"><a class="a04002" href="listMethodTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol2 %> ">Category</a>
       </td>
       <td class="td044CC001"><img src="<%= sortorderimagee %>"><img src="<%= sortorderimagef %>"><a class="a04002" href="listMethodTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol3 %> ">Method Title</a>
       </td>
       <td class="td044CC001"><img src="<%= sortorderimageg %>"><img src="<%= sortorderimageh %>"><a class="a04002" href="listMethodTTSpecs.jsp?<%= parmResend %>&orderby=<%= sortordercol4 %> ">Revision Date</a>
       </td>
       <td class="td044CC001"><b>Status</b>
       </td>
       </tr>

<%
    try
     {
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                                    whereStatement +
                                    " ORDER BY " + sortOrder );

   while (rs.next()) {

       noRecords = false;
%>
     <tr class="tr00001">
       <td class="td041CR002">
         M-<a class="a04002" href="../Router/methodRouter.jsp?method=<%= rs.getString("SPNMETH")%>&revised=<%= rs.getString("SPNREVIS") %>" target="_blank">
              <%= rs.getString("SPNMETH")%>
           </a>&nbsp;
       </td>
       <td class="td041CR002"><%= rs.getString("SPNCAT")%> &nbsp;
       </td>
       <td class="td041CL002"><%= rs.getString("SPNTITLE")%> &nbsp;
       </td>
       <td class="td047CR002"><%= rs.getString("SPNREVIS")%> &nbsp;
       </td>
       <td class="td047CR002"><%= rs.getString("SPNRECST") %> &nbsp;
       </td>
     </tr>
<% } // end while - start of error message if no records found

     if (noRecords) 
{ %>
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
<jsp:include page="../include/heading.jsp"></jsp:include>  

</body>
</html>