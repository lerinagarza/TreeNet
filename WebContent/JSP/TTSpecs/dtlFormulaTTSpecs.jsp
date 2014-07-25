<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.math.*" %>
<%@ page import="com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import="com.ibm.as400.access.*" %>
<%
//---------------- dtlFormulaTTSpecs.jsp ---------------------------------------//
//    Display screen jsp for Active Formula                                
//
//  Author :  Teri Walton  6/03/02                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/21/11   TWalton	   Change AS400 access to be without IP address
//  10/13/08   TWalton	   Point to NEW AS400
//  2/23/04    TWalton     Changed comments and images for 5.0 server.
//  5/06/03    TWalton     Fix no Detail Problem, ticket #5118
//  2/11/03    TWalton     More Printer Friendly (6690)               
// 12/19/02    TWalton     Moved Live to use new improved files       
//  9/06/02    TWalton     Change to Reflect Current Standards        
//------------------------------------------------------------------------------//
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
  
  boolean noRecords;

   String WhereStatement = "";

   public String addslash(String indate) {

                        String year = indate.substring(0,4);
                        String month = indate.substring(4,6);
                        String days = indate.substring(6,8);
                        indate = month + "/" + days + "/" + year;
                        return indate;
   }
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
//  sess.removeAttribute("ttiTheURL");
//   sess.removeAttribute("ttiSecStatus");

//*********************************************************************

//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************
   String parmFormula = request.getParameter("formula");
   if  (parmFormula == null || parmFormula.equals(""))
      parmFormula = "";

   String parmRevised = request.getParameter("revised");
   if  (parmRevised == null || parmRevised.equals(""))
       WhereStatement = "";
   else
       WhereStatement = "AND SPLREVIS <= " + parmRevised + " ";

//****
// Reset all the Displayed Information (Parameters)
//****
   String saveFormula = "";
   String saveName = "";
   String saveRevised = "";
   String saveSuper = "";
   String saveDetail = "";
   String saveStatus = "";
   String saveComent = "";
   String saveRecSt = "";
   String saveProg = "";
   String saveDate = "";
   String saveTime = "";
   String newSaveDate = "";
   String newSaveTime = "";
   String saveUser = "";
   String newSaveRevised = "";
   String newSaveSuper = "";
   noRecords = true;

//****
// Load all the Displayed Information (Parameters)
//****

   try
   {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLLFRM1 " +
                                "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 WhereStatement + " " +
                                "AND SPLRECST <> 'PENDING   ' " +
                                "ORDER BY SPLREVIS DESC, SPLRECST");

      if (rs.next())
      {
         saveFormula = rs.getString("SPLFORM");
         saveName = rs.getString("SPLNAME");
         saveRevised = rs.getString("SPLREVIS");
         saveDetail = rs.getString("SPLDETAIL").trim();
         if (saveDetail.equals(""))
           saveDetail = "There is No Detail for this Formula!";
         saveStatus = rs.getString("SPLSTATUS");
         saveRecSt = rs.getString("SPLRECST");
         saveComent = rs.getString("SPLCOMENT").trim();
         saveProg = rs.getString("SPLPROG");
         saveDate = rs.getString("SPLDATE");
         saveTime = rs.getString("SPLTIME");
         saveUser = rs.getString("SPLUSER");
         noRecords = false;

         if  (rs.next())
         {
         saveSuper = rs.getString("SPLREVIS");
         }

         //*****
         // Date Masking
         //*****
         if  (saveRevised.length()==8)
            newSaveRevised = addslash(saveRevised);
         else
            newSaveRevised = " ";

         if  (saveSuper.length()==8)
            newSaveSuper = addslash(saveSuper);
         else
            newSaveSuper = " ";

         if  (saveDate.length()==8)
            newSaveDate = addslash(saveDate);
         else
            newSaveDate = " ";

         //*****
         // Time Masking
         //*****

         if (saveTime.length()==6)
         {
            String hour = saveTime.substring(0,2);
            String min = saveTime.substring(2,4);
            String second = saveTime.substring(4,6);
            newSaveTime = hour + ":" + min + ":" + second;
         }
         else
         {
            if (saveTime.length()==5)
            {
               String hour = saveTime.substring(0,1);
               String min = saveTime.substring(1,3);
               String second = saveTime.substring(3,5);
               newSaveTime = hour + ":" + min + ":" + second;
            }
            else
               newSaveTime = " ";
         }
      }

      rs.close();
      stmt.close();
   } catch (Exception e)
     {
     out.println("Regular error : " + e);
     }
 

%>

<html>
   <head>
      <title>Detailed Formula by Formula Number</title>

      <link rel="stylesheet" type="text/css" href="https://image.treetop.com/webapp/Stylesheet.css" />

<script language="JavaScript1.2">
<!--

var head="display:'none'"
imageURL="https://image.treetop.com/webapp/minusbox3.gif";
imageURL1="https://image.treetop.com/webapp/plusbox3.gif";
imageURL2="https://image.treetop.com/webapp/plusbox3.gif";

function doit(header){
var head=header.style
  if (head.display=="none")
  {
   head.display=""
  }
  else  {
   head.display="none"
   }
}
function changeImage() {
        if(imageURL=="https://image.treetop.com/webapp/plusbox3.gif") {
                imageURL = "https://image.treetop.com/webapp/minusbox3.gif";
        }
        else {
                imageURL = "https://image.treetop.com/webapp/plusbox3.gif";
        }
        document.images[2].src = imageURL;
}
function changeImage1() {
        if(imageURL1=="https://image.treetop.com/webapp/plusbox3.gif") {
                imageURL1 = "https://image.treetop.com/webapp/minusbox3.gif";
        }
        else {
                imageURL1 = "https://image.treetop.com/webapp/plusbox3.gif";
        }
        document.images[3].src = imageURL1;

}
function changeImage2() {
        if(imageURL2=="https://image.treetop.com/webapp/plusbox3.gif") {
                imageURL2 = "https://image.treetop.com/webapp/minusbox3.gif";
        }
        else {
                imageURL2 = "https://image.treetop.com/webapp/plusbox3.gif";
        }
        document.images[4].src = imageURL2;

}
//-->
      </script>

      <script type="text/javascript">
             function go(form)
             {location=form.selectmenu.value}
<!-- Begin

function showRemote() {
self.name = "main"; // names current window as "main"

var windowprops = "toolbar=0,location=0,directories=0,status=0, " +
"menubar=0,scrollbars=0,resizable=0,width=300,height=200";

OpenWindow = window.open("selFormula.jsp", "remote", windowprops); // opens remote control
}
//  End -->

      </script>
   </head>

   <body>

<!------------------------------------------------------------------------------->
<!-- GOLD Header With Links                          -->
<!------------------------------------------------------------------------------->

  <table class="table03001" cellspacing="0">
     <tr>
        <td rowspan="3" style="width:125px" align="center">
         <img src="https://image.treetop.com/webapp/TTLogoSmall.gif">
        </td>

        <td class="td04SC002" colspan="6" style="width:70%">
         Formulation Information
        </td>

        <td rowspan="3" style="width:125px" align="center">
         <img src="https://image.treetop.com/webapp/TTJuiceBottle2.gif">
        </td>
     </tr>
      <tr>
         <td class="td046CC001"><b>
          Approval signatures on file at Quality Services<br>
          111 S. Railroad Ave. Selah, WA  98942
         </b></td>
      </tr>
      <tr class="tr03001">
         <td class="td00001" colspan="6">
          <table>
             <tr class="tr00001">
                <td class="td048CL001" style="width:25%">
                 <a class="a04001" href="https://treenet.treetop.com">Applications Home</a>
                </td>
                <td  class="td048CC001" style="width:25%">
                 <a class="a04001" href="#" onClick="showRemote();">Select New Formula</a>
                </td>
                <td class="td048CC001" style="width:25%">
<%
		// get authority object for this session
		String auth = request.getHeader("Authorization");
		
		// get Server
		String theHost = request.getHeader("Host");
		
		// get URI
		String theLongUrl = theHost + request.getRequestURI();
		int xxx = theLongUrl.indexOf("?");
		String theUrl = "";
		
		if (xxx == -1)
			theUrl = theLongUrl;
		else
			theUrl = theLongUrl.substring(0,xxx);
			
		// get current user profile
		String user = com.treetop.SessionVariables.getSessionttiProfile(request, response);
		
		// get current system date
		String[] dateAndTime = com.treetop.SystemDate.getSystemDate();

		// get hit counter information		
		java.sql.Date oneTimeDate = java.sql.Date.valueOf(dateAndTime[7]);
		String secSys = SessionVariables.getSessionSecuritySystem(request, response);
		if (secSys == null)
			secSys = "";
		String secVal = SessionVariables.getSessionSecurityValue(request, response);
		if (secVal == null)
			secVal = "";
		String secUsr = SessionVariables.getSessionSecurityUser(request, response);
		if (secUsr == null)
			secUsr = "";

		Counter.addHit(theUrl,user,oneTimeDate,
					   secSys, secVal, secUsr);
		
		int totalHits = Counter.findSumOfHitsByUrl(theUrl);
		
  //                   <jsp:include page="/servlet/com.treetop.servlets.Counter" />
  		
        String hitsPage = theHost + "/web/JSP/countResults.jsp";
        hitsPage = hitsPage + "?theurl=" + theLongUrl;
        hitsPage = hitsPage + "&thedate=" + dateAndTime[7];
        hitsPage = hitsPage + "&theuser=" + user;
        String xx = SessionVariables.getSessionttiProfile(request, response);
        String yy = SessionVariables.getSessionttiUserID(request, response);
        
        hitsPage = hitsPage + "&thecount=" + totalHits; 		

        out.println("<a class=\"a04001\" href=http://" +
            hitsPage + " target=\"_blank\"> Visited " + totalHits + " times </a> <br>");
%>
<%
           //       <jsp:include page="/servlet/com.treetop.servlets.Counter" />
					
%>                

                </td>
                <td class="td048CR001" style="width:25%">
                 <a class="a04001" href="mailto:helptdesk@treetop.com?Subject=CPG TT Specs">Help</a>
                </td>
             </tr>
          </table>
         </td>
      </tr>
   </table>

<!------------------------------------------------------------------------>
<!---- Header of Display                         -->
<!------------------------------------------------------------------------>
   <table class="table01001">
      <tr>
         <td class="td046CL001"><b>Formula Number :  </b></td>
         <td class="td073CL001">
           <a class="a04002" href="../Router/formulaRouter.jsp?formula=<%= saveFormula %>&revised=<%= saveRevised %>" target="_blank"><%= saveFormula %></a>
         </td>
         <td class="td046CL001"><b>Name :</b></td>
         <td class="td076CL001"><%= saveName %></td>
         <td class="td046CL001"><b>Formula/Revised Date Status :  </b></td>
         <td class="td076CL001"><%= saveRecSt %></td>
      </tr>
      <tr>
         <td class="td046CL001"><b>Revision Date :  </b></td>
         <td class="td076CL001"><%= newSaveRevised %></td>
         <td class="td046CL001"><b>Supercede Date :  </b></td>
         <td class="td076CL001"><%= newSaveSuper %></td>
         <td class="td046CL001"><b>Production Status :  </b></td>
         <td class="td076CL001"><%= saveStatus %></td>
      </tr>
   </table>

<!------------------------------------------------------------------------------------------------------------------->
<!--- Formula Details                                                                   -------------------->
<!------------------------------------------------------------------------------------------------------------------->
<b>
<div style="color:#990000; font-family:arial; font-size:10pt; text-align:left; background-color:#ffffcc">
<img src="https://image.treetop.com/webapp/minusbox3.gif" style="cursor:hand" onClick="doit(document.all[this.sourceIndex+1]); changeImage();">
Formula Details
</div></b>
<span style=&{head};>
   <table class="table01001">
     <tr>
         <td width="5%"></td>
         <td>
<%
   //**** Test to know when to do page breaks and such
    int testlength = saveDetail.length();
    BigDecimal approxperLine = new BigDecimal("35");
    BigDecimal textareaRows = (new BigDecimal(testlength)).divide(approxperLine,0);
    char[] newtestDetail = saveDetail.toCharArray();
    byte[] newtestbytes = saveDetail.getBytes();
    byte thirteen = 13;
    int[] endofLine = new int [testlength];
    endofLine[0] = 0;

    int linecount = 0;
    for (int i=0; i < testlength; i++)
    {
       String byteString = java.lang.Byte.toString(newtestbytes[i]);
       if (byteString.equals("13"))
       {
          linecount = linecount + 1;
          endofLine[linecount] = i;
       }
    }
          linecount = linecount + 1;
          endofLine[linecount] = testlength;
    String newDetail = "";
    int first = 0;
    for (int i=0; i < linecount; i++)
    {

      int testbegin = (endofLine[i] + 2);
      if (first == 0)
      {
         first = first + 1;
         testbegin = 0;
      }
      int testend = endofLine[i + 1];
      String testline = saveDetail.substring(testbegin,testend);
      if (testline.length() == 0)
      {
         newDetail = newDetail + "&nbsp;<br>";
      }
      else
      {
         if (testline.length() > 85)
         {
            String testline1 = testline.substring(0,85);
            int findblank = testline1.lastIndexOf(" ");
            testline1 = testline.substring(0, findblank);
            newDetail = newDetail + testline1 + "<br>";

            String testline2 = testline.substring(findblank,testline.length());
            String doneyet = "NO";
            while (doneyet.equals("NO"))
            {
               if (testline2.length() > 85)
               {
                  testline1 = "";
                  testline1 = testline2.substring(0,85);

                  findblank = testline1.lastIndexOf(" ");
                  testline1 = testline2.substring(0, findblank);

                  newDetail = newDetail + testline1 + "<br>";
                  testline2 = testline2.substring(findblank,testline2.length());
               }
               else
               {
                  newDetail = newDetail + testline2 + "<br>";
                  doneyet = "YES";
               }
            }
         }
         else
         newDetail = newDetail + testline + "<br>";
      }
   }
%>
            <pre><%= newDetail %></pre>
         </td>
       </tr>
   </table>
      </span>
<!------------------------------------------------------------------------------------------------------------------->
<!---  COMMENTS SECTIONS                                                                         -------------------->
<!------------------------------------------------------------------------------------------------------------------->
<b>
<div style="color:#990000; font-family:arial; font-size:10pt; text-align:left; background-color:#ffffcc">
<img src="https://image.treetop.com/webapp/plusbox3.gif" style="cursor:hand" onClick="doit(document.all[this.sourceIndex+1]); changeImage1();">
Comments
</div></b>
<span style="display:none" style=&{head};>
   <table class = "table01001">
      <tr>
         <td width="5%"></td>
         <td>
          <textarea rows="3" cols="80" style="font-family:courier; font-size:8pt; color:#000000; background-color:#fff7e7" readonly>
                 <%= saveComent %></textarea>
         </td>
      </tr>
   </table>
   </span>
<!------------------------------------------------------------------------------------------------------------------->
<!---  Update Information                                                                                          -------------------->
<!------------------------------------------------------------------------------------------------------------------->
<b>
<div style="color:#990000; font-family:arial; font-size:10pt; text-align:left; background-color:#ffffcc">
<img src="https://image.treetop.com/webapp/plusbox3.gif" style="cursor:hand" onClick="doit(document.all[this.sourceIndex+1]); changeImage2();">
Last Changes - Information
</div></b>
<span style="display:none" style=&{head};>
   <table class = "table01001">
      <tr class= "tr01001">
         <td class="td046CR001" style="width:35%"><b>Last Program to Make Change to Record:</b></td>
         <td class="td076CL001" style="width:20%"><%= saveProg %></td>
         <td class="td076CL001" style="width:45%">&nbsp;</td>
      </tr>
      <tr>
         <td class="td046CR001"><b>Last Date of Change Made to Record:</b></td>
         <td class="td076CL001"><%= newSaveDate %></td>
      </tr>
      <tr>
         <td class="td046CR001"><b>Last Time of Change Made to Record:</b></td>
         <td class="td076CL001"><%= newSaveTime %></td>
      </tr>
      <tr>
         <td class="td046CR001"><b>Last User to Make Change to Record:</b></td>
         <td class="td076CL001"><%= saveUser %></td>
      </tr>
   </table>
      </span>
  <%@ include file="../include/footer.jsp" %>
<%
   if (noRecords) {
%>
      <script language="JavaScript">
      alert("Formula Not Found!")
      </script>
<% }
%>
   </body>
</html>