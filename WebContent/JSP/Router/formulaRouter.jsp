<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.ibm.as400.access.*" %>
<%
//------------------ formulaRouter.jsp-----------------------------------//
//  List of Places to go / associated to Formula Number/Revision Date
//                                                                      
//  Author :  Teri Walton  8/27/02                                      
//  moved to Production 12/19/02                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/21/11   TWalton	   Change AS400 access to be without IP address
//  10/13/08   TWalton	   Point to NEW AS400
//  2/23/04    TWalton     Changed comments and images for 5.0 server.

//-------------------------------------------------------------------------------//
%>
<%!
//******************************************************************************
//   Declare Variables
//******************************************************************************

   Connection conn = null;

 //  boolean noRecords;
 //  String whereStatement = "";
 //  String firstRecord = "";
 //  String saveRevised = "";
 //  String editRevised = "";

   String whereStatement = "";

   String saveRevised = "";
   String editRevised = "";

   String userProfile = null;
   String userPassword = null;

   public String addslash(String indate) {

                        String year = indate.substring(0,4);
                        String month = indate.substring(4,6);
                        String days = indate.substring(6,8);
                        indate = month + "/" + days + "/" + year;
                        return indate;
   }
%>
<%
//**************************************************************************//
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title","Formula Information Available");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "");
  
//******************************************************************************
//   Get input fields
//******************************************************************************
//   String sys = ("10.6.100.3"); // 10/21/11 twalton - change to NOT use IP address
   String sys = ("lawson.treetop.com");
   String userProfile = ("DAUSER");
   String password = ("WEB230502");

   String rname = request.getParameter("resource");
   String cname = request.getParameter("formula");

   String driver = "com.ibm.as400.access.AS400JDBCDriver";

   Driver drv = (Driver) Class.forName(driver).newInstance();

   Properties prop = new Properties();
   prop.put("user", userProfile);
   prop.put("password", password);

   conn = DriverManager.getConnection("jdbc:as400:" + sys, prop);

//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************

   String parmFormula = request.getParameter("formula");
   if  (parmFormula == null || parmFormula.equals(" "))
      parmFormula = " ";

   String parmRevised = request.getParameter("revised");
   if  (parmRevised == null || parmRevised.trim().equals(""))
   {
      parmRevised = "";
      whereStatement = "";
   }
   else
      whereStatement = "AND SPLREVIS = " + parmRevised + " ";
%>

<html>

   <body>
   <jsp:include page="../include/heading.jsp"></jsp:include>
  <table class="table00001">
<%
   //*****
   // Retrieve Method Information, based on Parameters
   //*****
   try
   {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * " +
                      "FROM dbprd.SPLLFRM1 " +
                        "WHERE SPLFORM = \'" + parmFormula + "\' " +
                        whereStatement + " " +
                         " ORDER BY SPLRECST");
      if  (rs.next())
      {
         saveRevised = rs.getString("SPLREVIS");
         //*** Date Mask - for Revison Date
         if  (saveRevised.length()==8)
            editRevised = addslash(saveRevised);
         else
            editRevised = " ";
%>
      <tr>
         <td class="td042CL001"><b>Formula Queried: </b></td>
         <td class="td072CL001"><b><%= rs.getString("SPLFORM") %> &nbsp;&nbsp; <%= rs.getString("SPLNAME") %>&nbsp;&nbsp;
                 Revision Date: <%= editRevised %></b></td>
         <td class="td072CL001"><b>Status: <%= rs.getString("SPLRECST") %></b></td>
      </tr>
   </table>
   <table class="table01001">
<!------------------------------------------------------------------------------->
<!--   Inquiries...                                                            -->
<!------------------------------------------------------------------------------->
      <tr class="tr02001">
         <td class="td042CL001" colspan="2"><b>Inquiries...</b></td>
      </tr>
      <tr>
         <td class="td076CL001" colspan="2">
            <a class="a04002" href="../TTSpecs/dtlFormulaTTSpecs.jsp?formula=<%= parmFormula %>&revised=<%= parmRevised %>">
               Detail of TTSpec Formula Information</a>
                - View Details for Formula for TTSpecs System.
         </td>
      </tr>
      <tr>
         <td class="td076CL001" colspan="2">&nbsp;&nbsp;</td>
      </tr>
<%
  if (0 == 1)
  { // Remove the Searches Section
//<!------------------------------------------------------------------------------->
//<!--   Searches ...                                                            -->
//<!------------------------------------------------------------------------------->
%>
  

<%
  }
      }
      else
      {
%>
         <script language="JavaScript">
         alert("Formula Not Found!")
         </script>
<%
      }
      rs.close();
   } catch (SQLException e) {
           out.println("SQL error : " + e);
     }
     out.flush();
%>
   </table>

    <%@ include file="../include/footer.jsp" %>

   </body>
</html>