<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.ibm.as400.access.*" %>
<%
//------------------ updMethodTTSpecs.jsp ------------------------------//
//
// Author :  Teri Walton  9/17/02                                         
//   moved to Production 12/19/02
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/21/11   TWalton	   Change AS400 access to be without IP address
//  10/13/08   TWalton	   Point to NEW AS400
//  2/25/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------//
//**************************************************************************//
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Create New Method");
   String parameterList = "&returnToPage=/web/TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "");
//********************************************************************
// Execute security servlet.
//********************************************************************
   // Allow Session Variable Access
   HttpSession sess = request.getSession(true);
		
   // Set the Status
   SessionVariables.setSessionttiSecStatus(request,response,"");
		
   // Set the URL address used by the security servlet.
   String urlAddress = "/web/JSP/TTSpecs/updMethodTTSpecs.jsp(USE)";
   SessionVariables.setSessionttiTheURL(request, response, urlAddress);

   // Call the security Servlet
   getServletConfig().getServletContext().
   getRequestDispatcher("/TTISecurity" ).
   include(request, response);

   // Decision of whether or not to use the Inq, List or Detail
   if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
   {
      String x = "Authority was not granted for this page " +
                 "(Update Formula for CPG Specifications). " +
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

   String saveMethod = "";
   String saveCat = "";
   String saveTitle = "";
   String saveRevised = "";
   String saveRevisedEdit = "";
   String saveSupercede = "";
   String saveDetail = "";
   String saveStatus = "";
   String saveComent = "";
   String saveRecStatus = "";
   String saveProgram = "";
   String saveDate = "";
   String newSaveDate = "";
   String saveTime = "";
   String newSaveTime = "";
   String saveUser = "";
   String WhereStatement = "";

   String supercedeDate = "";
   String pendRecord = "";
   String pendYES = "";
   String submitValue= "";

   public String addslash(String indate) {

                        String year = indate.substring(0,4);
                        String month = indate.substring(4,6);
                        String days = indate.substring(6,8);
                        indate = month + "/" + days + "/" + year;
                        return indate;
   }

%>
<%
//******************************************************************************
//   Get input fields
//******************************************************************************
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
//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************
//****
//  Session Variables  ***************
//****

   HttpSession ssn = request.getSession(true);

   String ssnCat = (String) ssn.getAttribute("ssnmcat");
   if  (ssnCat == null)
      ssnCat = " ";

   String ssnTitle = (String) ssn.getAttribute("ssnmtitle");
   if  (ssnTitle == null)
      ssnTitle = " ";

   String ssnDetail = (String) ssn.getAttribute("ssnmdetail");
   if  (ssnDetail == null)
      ssnDetail = " ";

   String ssnComent = (String) ssn.getAttribute("ssnmcoment");
   if  (ssnComent == null)
      ssnComent = " ";

   String ssnNote = (String) ssn.getAttribute("ssnmnote");
   if  (ssnNote == null)
      ssnNote = " ";

   String ssnMode = (String) ssn.getAttribute("ssnmmode");
   if  (ssnMode == null)
      ssnMode = " ";

//****
//  Reset the Session Variables  ***************
//****
      ssn.removeAttribute("ssnmnote");
      ssn.removeAttribute("ssnmcat");
      ssn.removeAttribute("ssnmtitle");
      ssn.removeAttribute("ssnmdetail");
      ssn.removeAttribute("ssnmcoment");
      ssn.removeAttribute("ssnmmode");

//****
//  Incoming Parameter Variables  ***
//****

   String parmMode = request.getParameter("mode");
   if  (parmMode == null || parmMode.equals(""))
      parmMode = " ";

   String parmMethod = request.getParameter("method");
   if  (parmMethod == null || parmMethod.equals(""))
         parmMethod = " ";

   String parmRevised = request.getParameter("revised");
   if  (parmRevised == null || parmRevised.equals(""))
      parmRevised = " ";

//****
// Get AS400 System Date
//****

   String dateArray[] = SystemDate.getSystemDate();
   String todayDate = dateArray[3];
   int saveDateInt = Integer.parseInt(todayDate);

//****
// Reset all the Displayed Information (Parameters)
//****

   saveMethod = "";
   saveCat = "";
   saveTitle = "";
   saveRevised = "";
   saveDetail = "";
   saveComent = "";
   saveProgram = "";
   saveDate = "";
   saveTime = "";
   saveUser = "";
   saveRecStatus = "";

   supercedeDate = "";
   WhereStatement = "";
   submitValue = "";
   pendRecord = "";
   pendYES = "";

//****
// Load all the Displayed Information (Parameters)
//****
   //****
   // Check for Errors - Reload the Session Variables
   //****
   if  (ssnMode.equals("ERMETHN") || ssnMode.equals("ERCAT")   ||
        ssnMode.equals("ERMETH")  || ssnMode.equals("ERREVIS") ||
        ssnMode.equals("ERRREV")  || ssnMode.equals("ERRADD")  ||
        ssnMode.equals("ERRDAT"))
   {
      saveMethod = parmMethod;
      saveCat = ssnCat;
      saveTitle = ssnTitle.trim();
      saveRevised = parmRevised;
      saveDetail = ssnDetail.trim();
      saveComent = ssnComent.trim();
   }
   else
   {
      if  (parmMethod.equals(" "))
         parmMode = "add";

      if  (!parmMode.equals("add"))
      {
         //****
         // Does this Method have a Pending Record?
         //****
         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                     "WHERE SPNMETH = " + parmMethod + " " +
                     "AND SPNRECST = 'PENDING   ' " +
                     "ORDER BY SPNREVIS DESC");
            if (rs.next())
            {
               pendRecord = "YES";
               if (parmMode.equals("gopend"))
               {
                  parmMode = " ";
                  parmRevised = rs.getString("SPNREVIS");
               }
            }

            rs.close();
            stmt.close();
         } catch (SQLException e) {
            out.println("SQL error : " + e);
            }

         //****
         // Find Specific Record or only the newest record in the Method.
         //****
         if  (parmRevised.equals(" "))
            WhereStatement = " ";
         else
            WhereStatement = "AND SPNREVIS <= " + parmRevised + " ";

         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                  "WHERE SPNMETH = " + parmMethod + " " +
                   WhereStatement + " " +
                  "ORDER BY SPNREVIS DESC");
            if (rs.next())
            {
               //****
               // Load into Display the Fields from the First Record Found
               //****
               saveMethod = rs.getString("SPNMETH");
               saveCat = rs.getString("SPNCAT");
               saveTitle = rs.getString("SPNTITLE").trim();
               saveRevised = rs.getString("SPNREVIS");
               parmRevised = rs.getString("SPNREVIS");
               saveDetail = rs.getString("SPNDETAIL").trim();
               saveComent = rs.getString("SPNCOMENT").trim();
               saveRecStatus = rs.getString("SPNRECST");
               saveProgram = rs.getString("SPNPROG");
               saveDate = rs.getString("SPNDATE");
               saveTime = rs.getString("SPNTIME");
               saveUser = rs.getString("SPNUSER");
            }
            if (rs.next())
            {
               //****
               // Load into Display the Supercedes Date from the Second Record Found
               //****
               saveSupercede = rs.getString("SPNREVIS");
               if  (saveSupercede.length()==8)
                  supercedeDate = addslash(saveSupercede);
               else
                  supercedeDate = " ";
            }

            rs.close();
            stmt.close();
         } catch (SQLException e) {
            out.println("SQL error : " + e);
            }
      }
      if  (parmMode.equals("rev") || parmMode.equals("addnew"))
         saveRevised = "";
   }

   if (saveRevised.equals(""))
      saveRevised = todayDate;

   //*** Date Mask
   if  (saveRevised.length()==8)
      saveRevisedEdit = addslash(saveRevised);
   else
      saveRevisedEdit = " ";

   if  (saveRecStatus.equals("ACTIVE    ") && pendRecord.equals("YES"))
      pendYES = "Has Pending Method";

//***************************************************************************************************//
	String sendTitle = "Display " + saveRecStatus + " Method Information <br>" +
         "for Method M-" + saveMethod;
   if (parmMode.equals("add") || parmMode.equals("rev") || parmMode.equals("addnew"))  
      sendTitle = "Method Information <br>" +
         "<font color=\"990000\">Create New Method</font>";
   if (parmMode.equals("update"))  
   	  sendTitle = "Method Information <br>" +
         "<font color=\"990000\">Update " + saveRecStatus +
         " Method M-" + saveMethod + "</font>";
   if (parmMode.equals("delete"))  
   	  sendTitle = "Method Information <br>" +
         "<font color=\"990000\">Delete " + saveRecStatus + 
         " Method M-" + saveMethod + "</font>";
   if (parmMode.equals("approve"))  
   	   sendTitle = "Method Information <br>" +
         "<font color=\"990000\">Approve " + saveRecStatus +
         "Method M-" + saveMethod + "</font>";
   if (parmMode.equals("inactivate"))  
       sendTitle = "Method Information <br>" +
         "<font color=\"990000\">Inactivate " +
         saveRecStatus + " Method M-" + saveMethod + "</font>";

//*****************************************************************************
%>

<html>
   <head>
      <title>Edit - Add/Update Method by Method Number</title>

<script language="JavaScript1.2">
<!--

var head="display:'none'"
imageURL="https://image.treetop.com/webapp/plusbox3.gif";

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

OpenWindow = window.open("selMethod.jsp?update=yes", "remote", windowprops); // opens remote control
}
//  End -->

      </script>

   </head>

   <body>
   <jsp:include page="../include/heading.jsp"></jsp:include>  
   <table class="table01001" cellspacing="0">
    <tr>
      <td class="td048CL001" style="width:25%">
         <a class="a04001" href="#" onClick="showRemote();">Select New Method</a>
      </td>
    </tr>
</table>   
<%
   if (!parmMethod.equals(" ") && parmRevised.equals(" "))
   {
%>
         <script language="JavaScript">
         alert("Method Not Found!")
         </script>
<%
      parmMode = "add";
      saveMethod = parmMethod;
   }
//*****
// parmMode is Blank = First Time, before Task is Chosen ************//
//*****
   if (parmMode.equals(" "))
   {
%>
   <table class="table01001">
      <tr>
         <td class="td046CL001" style="width:2%"></td>
         <td class="td046CL001" style="width:25%"><b>Status:</b>&nbsp;<%= saveRecStatus %>&nbsp;<%= pendYES %></td>
         <td class="td048CC001">
           <form>
             <select class="selupd" name="selectmenu" onchange="go(this.form)">
<!------------------------------------------------------------------------------->
<!--------- Drop down Box for choosing which task to preform -------------------->
<!------------------------------------------------------------------------------->
            <option>Choose Task to Perform:</option>
<%
//*****
// Record is Active
//*****
      if (saveRecStatus.equals("ACTIVE    "))
      {

%>
         <option value="updMethodTTSpecs.jsp?mode=add">Create New Method (Blank Screen)</option>
         <option value="updMethodTTSpecs.jsp?method=<%= saveMethod %>&revised=<%= saveRevised %>&mode=addnew">
         Copy M-<%= saveMethod %> <%= saveRevisedEdit %> to Create New Method</option>
<%
         if (pendRecord.equals("YES"))
         {
%>
            <option value="updMethodTTSpecs.jsp?method=<%= saveMethod %>&mode=gopend">
            Pending Version of M-<%= saveMethod %>  <%= saveRevised %>
            </option>
<%
         }
         else
         {
%>
            <option value="upd2MethodTTSpecs.jsp?method=<%= saveMethod %>&revised=<%= saveRevisedEdit %>&mode=rev">
            Create a New Revision of M-<%= saveMethod %>
            </option>
<%
         }
%>
         <option value="updMethodTTSpecs.jsp?method=<%= saveMethod %>&revised=<%= saveRevised %>&mode=inactivate">
         In-Activate M-<%= saveMethod %>
         </option>
<%
      }//end active

      else  //not active
      {
//
// ** IF RECORD IS INACTIVE ****************************************//
//
         if (saveRecStatus.equals("INACTIVE  "))
         {
%>
            <option value="updMethodTTSpecs.jsp?mode=add">Create New Method (Blank Screen)</option>
            <option value="updMethodTTSpecs.jsp?method=<%= saveMethod %>&revised=<%= saveRevised %>&mode=addnew">
            Copy M-<%= saveMethod %> <%= saveRevisedEdit %> to Create New Method</option>
<%
            if (pendRecord.equals("YES"))
            {
%>
               <option value="updMethodTTSpecs.jsp?method=<%= saveMethod %>&mode=gopend">
               Pending Version of M-<%= saveMethod %>
               </option>
<%
            }
            else
            {
%>
               <option value="upd2MethodTTSpecs.jsp?method=<%= saveMethod %>&revised=<%= saveRevisedEdit %>&mode=rev">
               Create a New Revision of M-<%= saveMethod %>
               </option>
<%
            }

         }//end inactive

         else
         {
// ** IF RECORD IS PENDING ****************************************//
            if (saveRecStatus.equals("PENDING   "))
            {
%>
               <option value="updMethodTTSpecs.jsp?mode=add">Create New Method (Blank Screen)</option>
               <option value="updMethodTTSpecs.jsp?method=<%= saveMethod %>&revised=<%= saveRevised %>&mode=addnew">
                Copy M-<%= saveMethod %> <%= saveRevisedEdit %> to Create New Method</option>
               <option value="updMethodTTSpecs.jsp?method=<%= parmMethod %>&revised=<%= parmRevised %>&mode=update">
                Update M-<%= saveMethod %> <%= saveRevisedEdit %></option>
               <option value="updMethodTTSpecs.jsp?method=<%= parmMethod %>&revised=<%= parmRevised %>&mode=delete">
                Delete M-<%= saveMethod %> <%= saveRevisedEdit %></option>
               <option value="updMethodTTSpecs.jsp?method=<%= parmMethod %>&revised=<%= parmRevised %>&mode=approve">
                Approve M-<%= saveMethod %> <%= saveRevisedEdit %></option>
<%
            }//end pending
         }// end not inactive
      }// end not active
%>
             </select>
           </form>
         </td>
         <td width="25%">&nbsp;</td>
      </tr>
   </table>
<%
   }// endParmMode = Blank
   else
   {
// ** Parm Mode = other than Blank.

      if (parmMode.equals("add") || parmMode.equals("addnew"))
      {
         submitValue = "Add Method";
         saveRecStatus = " ";
      }

      if (parmMode.equals("rev"))
      {
         submitValue = "Add New Revision";
         saveRecStatus = " ";
      }

      if (parmMode.equals("update"))
         submitValue = "Update Method";

      if (parmMode.equals("delete"))
         submitValue = "Delete Method";

      if (parmMode.equals("approve"))
         submitValue = "Approve Method";

      if (parmMode.equals("inactivate"))
         submitValue = "De-Activate Method";
%>
   <table class="table01001">
   <form action="../TTSpecs/upd2MethodTTSpecs.jsp?" method="post">
     <tr><td class="td046CL001" style="width:2%"></td>
         <td class="td046CL001" style="width:20%"><b>Current Record Status:</b> &nbsp; <%= saveRecStatus %></td>
        <td class="td048CC001" style="width:52%">
            <input type="Submit" value="<%= submitValue %>">
         </td>
         <td width="25%">&nbsp;</td>
      </tr>

     </table>
<%
   }
%>

<%
   if (!submitValue.equals("Add Method"))
   {
%>

<b>
<div style="color:#990000; font-family:arial; font-size:10pt; text-align:left; background-color:#ffffcc">
<img src="https://image.treetop.com/webapp/plusbox3.gif" style="cursor:hand" onClick="doit(document.all[this.sourceIndex+1]); changeImage();">
Last Changes - Information
</div></b>
<span style="display:none" style=&{head};>
   <table class = "table01001" align="center">
      <tr class= "tr01001">

<%
//*** Date Mask
      if (saveDate.length()==8)
         newSaveDate = addslash(saveDate);
      else
         newSaveDate = " ";


//*** Time Mask
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
         {
            newSaveTime = " ";
         }
      }
%>
         <td class="td046CL001" style="width:15%"> &nbsp;</td>
         <td class="td046CR001" style="width:40%"><b>Last Program to Make Change to Record:</b></td>
         <td class="td066CL001" style="width:30%"><%= saveProgram %></td>
         <td class="td076CL001" style="width:15%">&nbsp;</td>
      </tr>
      <tr>
         <td class="td046CL001"></td>
         <td class="td046CR001"><b>Last Date of Change Made to Record:</b></td>
         <td class="td066CL001"><%= newSaveDate %></td>
         <td class="td046CL001"></td>
      </tr>
      <tr>
         <td class="td046CL001"></td>
         <td class="td046CR001"><b>Last Time of Change Made to Record:</b></td>
         <td class="td066CL001"><%= newSaveTime %></td>
         <td class="td046CL001"></td>
      </tr>
      <tr>
         <td class="td046CL001"></td>
         <td class="td046CR001"><b>Last User to Make Change to Record:</b></td>
         <td class="td066CL001"><%= saveUser %></td>
         <td class="td046CL001"></td>
      </tr>
   </table>
      </span>

<%
   }
%>

   <table class="table01001">
      <tr>
         <td class="td046CL001" colspan="3">&nbsp; </td>
      </tr>
      <tr>
         <input type="hidden" name="mode" value="<%= parmMode %>">
         <td class="td046CL001" rowspan="7" style="width:2%"></td>
         <td class="td046CR001"><b>Method Number : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("addnew"))
   {
%>
         <td class="td073CL001"><b>M-</b>
           <input class="inupdl" type="text" name="method" size="14" value="<%= saveMethod %>">
           <font color="#990000">*</font>
         </td>
         <td class="td046CL001" rowspan="7" style="width:2%"></td>
<%
   } else
   {
%>
         <td class="td073CL001">
           <a class="a04002" href="../Router/methodRouter.jsp?method=<%= saveMethod %>&revised=<%= saveRevised %>" target="_blank">M-<%= saveMethod %></a>
           <font color="#990000">*</font>
         </td>
           <input type="hidden" name="method" size="14" value="<%= saveMethod %>">
<%
   }
%>
      </tr>
      <!--New Row  2-->
      <tr>
         <td class="td046CR001"><b>Category : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("rev") || parmMode.equals("addnew") || parmMode.equals("update"))
   {
%>
         <td class="td076CL001">
           <input class="inupdl" type="text" name="mcat" size="9" value="<%= saveCat %>">
         </td>
<%
   } else
   {
%>
         <td class="td073CL001"><%= saveCat %></td>
           <input type="hidden" name="mcat" size="9" value="<%= saveCat %>">
<%
   }
%>
      </tr>
      <!--New Row  3-->
      <tr>
         <td class="td046CR001"><b>Title : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("rev") || parmMode.equals("addnew") || parmMode.equals("update"))
   {
%>
         <td class="td076CL001">
           <input class="inupdl" type="text" name="mtitle" size="70" value="<%= saveTitle %>">
         </td>
<%
   } else
   {
%>
         <td class="td073CL001"><%= saveTitle %></td>
           <input type="hidden" name="mtitle" size="70" value="<%= saveTitle %>">
<%
   }
%>
      </tr>
      <!--New Row  4-->
      <tr>
         <td class="td046CR001"><b>Revision Date : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("rev") || parmMode.equals("addnew") || parmMode.equals("update"))
   {
%>
         <td class="td076CL001">
           <input class="inupdl" type="text" name="revised" size="10" value="<%= saveRevisedEdit %>">
           <font color="#990000">*</font>
         </td>
<%
   } else
   {
%>
         <td class="td073CL001"><%= saveRevisedEdit %><font color="#990000">*</font></td>
           <input type="hidden" name="revised" size="10" value="<%= saveRevisedEdit %>">
<%
   }
%>
      </tr>
      <!--New Row  5-->
      <tr>
         <td class="td046CR001"><b>Supercede Date : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("addnew") || parmMode.equals("rev"))
   {
%>
         <td class="td073CL001">&nbsp;</td>
<%
   } else
   {
%>
         <td class="td073CL001"><%= supercedeDate %></td>
<%
   }
%>
      </tr>
      <!--New Row  6-->
      <tr>

         <td class="td046CR001" valign="top"><b>Method Details : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("rev") || parmMode.equals("addnew") || parmMode.equals("update"))
   {
%>
         <td>
            <textarea class="taupd" name="mdetail" rows="20" cols="80">
                 <%= saveDetail %>
            </textarea>
         </td>
<%
   } else
   {
%>
         <td>
            <textarea class="tadis" name="mdetail" rows="20" cols="80" readonly>
                 <%= saveDetail %>
            </textarea>
         </td>
<%
   }
%>
      </tr>
      <!--New Row  7-->
      <tr>
         <td class="td046CR001" valign="top"><b>Comments : </b></td>
<%
   if (parmMode.equals("add") || parmMode.equals("rev") || parmMode.equals("addnew") || parmMode.equals("update"))
   {
%>
         <td>
            <textarea class="taupd" name="mcoment" rows="2" cols="80">
                 <%= saveComent %>
            </textarea>
         </td>
<%
   } else
   {
%>
         <td>
            <textarea class="tadis" name="mcoment" rows="2" cols="80" readonly>
                 <%= saveComent %>
            </textarea>
         </td>
<%
   }
%>
      </tr>
      <tr>
         <td>
         &nbsp;
         </td>
         <td>
         &nbsp;
         </td>
         <td>
            <font color="#990000"><i>* Required</i></font>
         </td>
      </tr>
<%
   if (!parmMode.equals(" "))
   {
%>
      </form>
<%
   }
%>
   </table>
<%
   if (!ssnNote.equals(" "))
   {
%>
      <script type="text/javascript">
      alert("<%= ssnNote %>")
      </script>
<%
   }
%>
<jsp:include page="../include/footer.jsp"></jsp:include>

   </body>
</html>