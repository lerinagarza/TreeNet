<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.math.*" %>
<%@ page import="com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import="com.ibm.as400.access.*" %>

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

   String saveMethod = "";
   String saveCat = "";
   String saveTitle = "";
   String saveRevised = "";
   String newSaveRevised = "";
   String saveSuper = "";
   String newSaveSuper = "";
   String saveDetail = "";
   String saveComent = "";
   String saveRecSt = "";
   String saveProg = "";
   String saveDate = "";
   String saveTime = "";
   String newSaveDate = "";
   String newSaveTime = "";
   String saveUser = "";

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
//------------------ dtlMethodTTSpecs.jsp ------------------------------//
//
// Author :  Teri Walton  7/15/02    
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/21/11   TWalton	   Change AS400 access to be without IP address
//  10/13/08   TWalton	   Point to NEW AS400
//  2/23/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------//

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
                 "(Detail CPG Methods). " +
                 "Please contact the Information Services Help Desk" ;
      response.sendRedirect("/web/TreeNetInq?msg=" + x);
      return;
   }
		
   //remove the Status and the Url
   sess.removeAttribute("ttiTheURL");
   sess.removeAttribute("ttiSecStatus");

//*********************************************************************
   
//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************
   String parmMethod = request.getParameter("method");
   if  (parmMethod == null || parmMethod.equals(""))
      parmMethod = " ";

   String parmRevised = request.getParameter("revised");
   if  (parmRevised == null || parmRevised.equals(""))
      parmRevised = " ";

//****
// Reset all the Displayed Information (Parameters)
//****
   saveMethod = "";
   saveCat = "";
   saveTitle = "";
   saveRevised = "";
   saveSuper = "";
   saveDetail = "";
   saveComent = "";
   saveRecSt = "";
   saveProg = "";
   saveDate = "";
   saveTime = "";
   newSaveDate = "";
   newSaveTime = "";
   saveUser = "";
   noRecords = true;

//****
// Load all the Displayed Information (Parameters)
//****
   if (parmRevised.equals(" "))
      WhereStatement = "AND SPNRECST = 'ACTIVE    ' ";
   else
      WhereStatement = "AND SPNREVIS <= " + parmRevised + " ";

   try
   {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                                "WHERE SPNMETH = " + parmMethod + " " +
                                 WhereStatement + " " +
                                "ORDER BY SPNREVIS DESC, SPNRECST");

      if (rs.next())
      {
         saveMethod = rs.getString("SPNMETH");
         saveCat = rs.getString("SPNCAT");
         saveTitle = rs.getString("SPNTITLE");
         saveRevised = rs.getString("SPNREVIS");
         saveDetail = rs.getString("SPNDETAIL").trim();
         saveRecSt = rs.getString("SPNRECST");
         saveComent = rs.getString("SPNCOMENT").trim();
         saveProg = rs.getString("SPNPROG");
         saveDate = rs.getString("SPNDATE");
         saveTime = rs.getString("SPNTIME");
         saveUser = rs.getString("SPNUSER");
         noRecords = false;

         if  (rs.next())
         {
         saveSuper = rs.getString("SPNREVIS");
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
   } catch (SQLException e)
     {
     out.println("SQL error : " + e);
     }

  
//***********************************************************************
%>
<html>
   <head>
      <title>Detailed Method by Method Number</title>

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

OpenWindow = window.open("selMethod.jsp", "remote", windowprops); // opens remote control
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
        <td rowspan="2" style="width:125px" align="center">
         <img src="https://image.treetop.com/webapp/TTLogoSmall.gif">
         </td>

        <td class="td04SC002" colspan="6" style="width:70%">
         Method Information
        </td>

        <td rowspan="2" style="width:125px" align="center">
         <img src="https://image.treetop.com/webapp/TTJuiceBottle2.gif">
       </td>
     </tr>
      <tr class="tr03001">
         <td class="td00001" colspan="6">
          <table>
             <tr class="tr00001">
                <td class="td048CL001" style="width:25%">
                 <a class="a04001" href="/web/TreeNetInq">TreeNet Home</a>
                </td>
                <td class="td048CC001" style="width:25%">
                 <a class="a04001" href="#" onClick="showRemote();">Select New Method</a>
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
         <td class="td046CR001"><b>Method Number :  </b></td>
         <td class="td073CL001">
           <a class="a04002" href="../Router/methodRouter.jsp?method=<%= saveMethod %>&revised=<%= saveRevised %>" target="_blank">M-<%= saveMethod %></a>
         </td>
         <td class="td046CR001"><b>Category :</b></td>
         <td class="td076CL001"><%= saveCat %></td>
         <td class="td046CR001"><b>Title :</b></td>
         <td class="td076CL001"><%= saveTitle %></td>
      </tr>
      <tr>
         <td class="td046CR001"><b>Revision Date :  </b></td>
         <td class="td076CL001"><%= newSaveRevised %></td>
         <td class="td046CR001"><b>Supercede Date :  </b></td>
         <td class="td076CL001"><%= newSaveSuper %></td>
         <td class="td046CR001"><b>Method/Revised Date Status :  </b></td>
         <td class="td076CL001"><%= saveRecSt %></td>
      </tr>
   </table>

<!------------------------------------------------------------------------------------------------------------------->
<!--- Method Details                                                                   -------------------->
<!------------------------------------------------------------------------------------------------------------------->
<b>
<div style="color:#990000; font-family:arial; font-size:10pt; text-align:left; background-color:#ffffcc">
<img src="https://image.treetop.com/webapp/minusbox3.gif" style="cursor:hand" onClick="doit(document.all[this.sourceIndex+1]); changeImage();">
Method Details
</div></b>
<span style=&{head};>
   <table class="table01001">
      <tr>
         <td width="5%"></td>
         <td>
<%
   String newDetail = "";
try
{
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
   }
   catch(Exception e)
   {
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
<!------------------------------------------------------------------------------->
<!--   Java Script Date (Bottom of Page)                                       -->
<!------------------------------------------------------------------------------->

   <table class="table02001" cellspacing="0">
      <tr>
         <td class="td046CC001">

            <script type="text/javascript">
            var d=new Date()
            var weekday=new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
            var monthname=new Array("January","February","March","April","May","June","July","August","September","October","November","December")
            document.write(weekday[d.getDay()] + ", ")
            document.write(monthname[d.getMonth()] + " ")
            document.write(d.getDate() + ", ")
            document.write(d.getFullYear())
            </script>

         </td>
      </tr>
   </table>
<%
   if (noRecords) {
%>
      <script language="JavaScript">
      alert("Method Not Found!")
      </script>
<% }
%>
   </body>
</html>