<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*, java.io.*, javax.sql.*"%>
<%@ page import="com.treetop.*" %>
<%@ page import="com.ibm.as400.access.*" %>
<%
//------------------ upd2MethodTTSpecs.jsp ------------------------------//
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

   String ErrorMode = "";
   String GoodMode = "";

   String outgoingParms = "";
   String outgoingResend = "";

   BigDecimal methodNum = new BigDecimal("0");
   BigDecimal catNum = new BigDecimal("0");

   int revDateNum = 0;

   String saveRevisEdit = "";

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

//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************

   ErrorMode = " ";
   GoodMode = " ";
   outgoingParms = "";

//***  Session Variables  ***************

   HttpSession ssn = request.getSession(true);

   ssn.removeAttribute("ssnmnote");
   ssn.removeAttribute("ssnmcat");
   ssn.removeAttribute("ssnmtitle");
   ssn.removeAttribute("ssnmdetail");
   ssn.removeAttribute("ssnmcoment");
   ssn.removeAttribute("ssnmmode");

//***  Incoming Parameter Variables  ***

   String parmMethod = request.getParameter("method");
   if  (parmMethod == null || parmMethod.equals("") || parmMethod.equals(" "))
   {
      parmMethod = "0.000";
      ssn.setAttribute("ssnmnote", "ERROR  --  No Method Entered.  Please enter a Method Number.");
      ErrorMode = "ERMETH";
   }
   else
   {
      try
      {
         methodNum =  new BigDecimal(parmMethod);
      }	
      catch (NumberFormatException nfe)
      {
         ssn.setAttribute("ssnmnote", "ERROR  --  Method: " + parmMethod + " is not a Number.");
         ErrorMode = "ERMETHN";
      }	
   }

   String parmRevised = request.getParameter("revised");
   String TestRevised[] = CheckDate.validateDate(parmRevised);
   if  (parmRevised == null || parmRevised.equals(""))
   {
      parmRevised = " ";
      ssn.setAttribute("ssnmnote", "ERROR  --  No Revision Date Entered.  Please enter a Revision Date.");
      ErrorMode = "ERREVIS";
   }
   else
   {
      if  (TestRevised[0].equals(""))
      {
         ssn.setAttribute("ssnmnote", "ERROR  --  " + TestRevised[6]);
         ErrorMode = "ERRREV";
      }
      else
      {
           //**  Create a Numeric Revised Date
         try
         {
            revDateNum = Integer.parseInt(TestRevised[4]);
         }	
         catch (NumberFormatException nfe)
         {  }
      }
   }

   String parmmCat = request.getParameter("mcat");
   if  (parmmCat == null || parmmCat.equals("") || parmmCat.equals("0") || parmmCat.equals(" "))
      parmmCat = " ";
   else
   {
      try
      {
         catNum =  new BigDecimal(parmmCat);
      }	
      catch (NumberFormatException nfe)
      {
         ssn.setAttribute("ssnmnote", "ERROR  --  Category: " + parmmCat + " is not a Number.");
         ErrorMode = "ERCAT";
      }	
   }

   String parmmTitle = request.getParameter("mtitle");
   if  (parmmTitle == null || parmmTitle.equals(""))
      parmmTitle = " ";

   String parmmDetail = request.getParameter("mdetail");
   if  (parmmDetail == null || parmmDetail.equals(""))
      parmmDetail = " ";

   String parmmComent = request.getParameter("mcoment");
   if  (parmmComent == null || parmmComent.equals(""))
      parmmComent = " ";

   String parmMode = request.getParameter("mode");
   if  (parmMode == null || parmMode.equals(""))
      parmMode = " ";

// Get AS400 System Date, Time & User

   String dateArray[] = SystemDate.getSystemDate();
   String saveTime = dateArray[0];
   int saveTimeInt = Integer.parseInt(saveTime);
   String saveDate = dateArray[3];
   int saveDateInt = Integer.parseInt(saveDate);

   String auth2 = request.getHeader("Authorization");
   String myProfile = Security.getProfile(auth2);

   if  (ErrorMode.equals(" "))
   {
//******************************************************************************
//   ADD New Method
//******************************************************************************

      if  (parmMode.equals("add") || parmMode.equals("addnew"))
      {
         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                                      "WHERE SPNMETH = " + parmMethod + " " +
                                      "ORDER BY SPNREVIS");
            if (rs.next())
            {
               //*****
               // Record Already Exists in the File
               //*****
               ssn.setAttribute("ssnmnote", "ERROR  --  Cannot add pending Method " + parmMethod + "Revised " + TestRevised[1] + ", already there.");
               ErrorMode = "ERRADD";
            }
            else
            {
	        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO dbprd.SPLNMET1 " +
	                          "(SPNMETH, SPNCAT, SPNTITLE, SPNREVIS, SPNDETAIL, " +
                                 "SPNCOMENT, SPNALUP, SPNRECST, SPNPROG, " +
                                 "SPNDATE, SPNTIME, SPNUSER, SPNWSN) " +
                                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

               pstmt.setBigDecimal(1, methodNum);
	        pstmt.setBigDecimal(2, catNum);
	        pstmt.setString(3, parmmTitle);
	        pstmt.setInt(4, revDateNum);
	        pstmt.setString(5, parmmDetail);
	        pstmt.setString(6, parmmComent);
	        pstmt.setString(7, "YES");
	        pstmt.setString(8, "PENDING   ");
	        pstmt.setString(9, "upd2MethodTTSpecs.jsp");
               pstmt.setInt(10, saveDateInt);
               pstmt.setInt(11, saveTimeInt);
               pstmt.setString(12, myProfile);
	        pstmt.setString(13, " ");

	        pstmt.executeUpdate();

               ssn.setAttribute("ssnmnote", "Thank you! Method " + parmMethod + "Revised " + TestRevised[1] + " has been added.");
               GoodMode = "ADD";
            }
            rs.close();
            stmt.close();
         } catch (SQLException e)
           {   }

      }

//******************************************************************************
//   ADD New Revision of a Method (Default in Today's Date as Revision Date)
//******************************************************************************

      if  (parmMode.equals("rev"))
      {

         //*****
         //   Copy Record from File and Insert another Record into the Same File
         //*****
         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                                 "WHERE SPNMETH = " + parmMethod + " " +
                                 "AND SPNREVIS = " + TestRevised[4] + " " +
                                 "ORDER BY SPNTITLE");
            if (rs.next())
            {
               PreparedStatement pstmt7 = conn.prepareStatement("INSERT INTO dbprd.SPLNMET1 " +
	                          "(SPNMETH, SPNCAT, SPNTITLE, SPNREVIS, SPNDETAIL, " +
                                 "SPNCOMENT, SPNALUP, SPNRECST, SPNPROG, " +
                                 "SPNDATE, SPNTIME, SPNUSER, SPNWSN) " +
                                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

               pstmt7.setBigDecimal(1, rs.getBigDecimal("SPNMETH"));
               pstmt7.setBigDecimal(2, rs.getBigDecimal("SPNCAT"));
               pstmt7.setString(3, rs.getString("SPNTITLE"));
               pstmt7.setInt(4, saveDateInt);
               pstmt7.setString(5, rs.getString("SPNDETAIL"));
               pstmt7.setString(6, rs.getString("SPNCOMENT"));
	        pstmt7.setString(7, "YES");
	        pstmt7.setString(8, "PENDING   ");
	        pstmt7.setString(9, "upd2MethodTTSpecs.jsp");
               pstmt7.setInt(10, saveDateInt);
               pstmt7.setInt(11, saveTimeInt);
               pstmt7.setString(12, myProfile);
	        pstmt7.setString(13, " ");

	        pstmt7.executeUpdate();

               ssn.setAttribute("ssnmnote", "Thank you! Method " + parmMethod + "NEW Revision " + saveDate + " has been added.");
               GoodMode = "REV";

               TestRevised[4] = "";
            }
            rs.close();
            stmt.close();
         } catch (SQLException e)
                 {   }
      }

//******************************************************************************
//   UPDATE/APPROVE ---   Every time a record is updated or approved there is
//                        a check to make sure that the date typed in as the parm
//                        is greater than that of the last revision.
//******************************************************************************

      if  (parmMode.equals("update") || parmMode.equals("approve"))
      {
         //*****
         //   Gets Active Record with the most current revision date
         //  to use the most current revision date to not let anyone
         //  use a date less than the most current.
         //*****
         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLNMET1 " +
                     "WHERE SPNMETH = " + parmMethod + " " +
                     "AND SPNRECST <> 'PENDING   ' " +
                     "ORDER BY SPNREVIS DESC");
            if (rs.next())
            {
               saveRevisEdit = rs.getString("SPLREVIS");
               if  (revDateNum <= rs.getInt("SPLREVIS"))
               {
                  ssn.setAttribute("ssnmnote", "Revision Date" + TestRevised[1] + " for " + parmMethod +
                                   " is Less Than or Equal to Active Method Date " + saveRevisEdit + ".");
                  ErrorMode = "ERRDAT";
               }
            }
            rs.close();
            stmt.close();
         } catch (SQLException e)
           {  }

         if (ErrorMode.equals(" "))
         {
            //*****
            // Approve Record
            //*****
            if  (parmMode.equals("approve"))
            {
               //*****
               //   First make sure all other active Records are set to Inactive.
               //       Only one active Record per Method.
               //*****
 	        PreparedStatement pstmt7 = conn.prepareStatement("UPDATE dbprd.SPLNMET1 " +
    	                         "SET SPNRECST = ?, SPNPROG = ?, SPNDATE = ?, SPNTIME = ?, " +
	                             "SPNUSER = ? " +
                                "WHERE SPNMETH = " + parmMethod + " " +
                                  "AND SPNRECST = 'ACTIVE    ' ");

               pstmt7.setString(1, "INACTIVE  ");
  	        pstmt7.setString(2, "upd2MethodTTSpecs.jsp");
               pstmt7.setInt(3, saveDateInt);
               pstmt7.setInt(4, saveTimeInt);
               pstmt7.setString(5, myProfile);

	        pstmt7.executeUpdate();

               //*****
               //   Update Pending Record to Active Status.
               //*****
	        PreparedStatement pstmt2 = conn.prepareStatement("UPDATE dbprd.SPLNMET1 " +
	                    "SET SPNRECST = ?, SPNPROG = ?, SPNDATE = ?, SPNTIME = ?, " +
	                        "SPNUSER = ? " +
                           "WHERE SPNMETH = " + parmMethod + " " +
                             "AND SPNRECST = 'PENDING    ' ");

               pstmt2.setString(1, "ACTIVE    ");
      	        pstmt2.setString(2, "upd2MethodTTSpecs.jsp");
               pstmt2.setInt(3, saveDateInt);
               pstmt2.setInt(4, saveTimeInt);
               pstmt2.setString(5, myProfile);

	        pstmt2.executeUpdate();

               ssn.setAttribute("ssnmnote", "Thank you! Method " + parmMethod + "Revised " + TestRevised[1] + " has been Approved.");
               GoodMode = "UPDATE";
            }
            else
            {
               //*****
               // Update Record
               //*****
   	        PreparedStatement pstmt1 = conn.prepareStatement("UPDATE dbprd.SPLNMET1 " +
  	                    "SET SPNCAT = ?, SPNTITLE = ?, SPNREVIS = ?, " +
  	                        "SPNDETAIL = ?, SPNCOMENT = ?, " +
  	                        "SPNPROG = ?, SPNDATE = ?, SPNTIME = ?, SPNUSER = ? " +
                           "WHERE SPNMETH = " + parmMethod + " " +
                                  "AND SPNRECST = 'PENDING   ' ");

               pstmt1.setBigDecimal(1, catNum);
               pstmt1.setString(2, parmmTitle);
               pstmt1.setString(3, TestRevised[4]);
	        pstmt1.setString(4, parmmDetail);
  	        pstmt1.setString(5, parmmComent);
               pstmt1.setString(6, "upd2MethodTTSpecs.jsp");
               pstmt1.setInt(7, saveDateInt);
               pstmt1.setInt(8, saveTimeInt);
               pstmt1.setString(9, myProfile);

	        pstmt1.executeUpdate();

               ssn.setAttribute("ssnmnote", "Thank you! Method " + parmMethod + "Revised " + TestRevised[1] + " has been updated.");
               GoodMode = "UPDATE";
            }
         }
      }

//******************************************************************************
//   INACTIVATE      Because there should only be one active record.
//                 Change all Active Records with this Method to Inactive
//******************************************************************************

      if  (parmMode.equals("inactivate"))
      {
         PreparedStatement pstmt4 = conn.prepareStatement("UPDATE dbprd.SPLNMET1 " +
	          "SET SPNRECST = ?, SPNPROG = ?, SPNDATE = ?, SPNTIME = ?, SPNUSER = ? " +
                               "WHERE SPNMETH = " + parmMethod + " " +
                               "AND SPNRECST = 'ACTIVE    ' ");

         pstmt4.setString(1, "INACTIVE  ");
	  pstmt4.setString(2, "upd2MethodTTSpecs.jsp");
         pstmt4.setInt(3, saveDateInt);
         pstmt4.setInt(4, saveTimeInt);
         pstmt4.setString(5, myProfile);

	  pstmt4.executeUpdate();

         ssn.setAttribute("ssnmnote", "Thank you! Method " + parmMethod + " has been Inactivated.");
         GoodMode = "UPDATE";
      }

//******************************************************************************
//   DELETE Pending Record from File
//******************************************************************************

      if  (parmMode.equals("delete"))
      {
      	  PreparedStatement pstmtdel = conn.prepareStatement("DELETE FROM dbprd.SPLNMET1 " +
                                   "WHERE SPNMETH = " + parmMethod + " " +
                                   "AND SPNRECST = 'PENDING   ' ");

  	  pstmtdel.executeUpdate();

         ssn.setAttribute("ssnmnote", "Thank you! Pending Method " + parmMethod + "Revised " + TestRevised[1] + " has been deleted.");
         GoodMode = "DELETE";
      }

//******************************************************************************

   }

   if  (!ErrorMode.equals(" "))
   {
      ssn.setAttribute("ssnmcat", parmmCat);
      ssn.setAttribute("ssnmtitle", parmmTitle);
      ssn.setAttribute("ssnmdetail", parmmDetail);
      ssn.setAttribute("ssnmcoment", parmmComent);
      ssn.setAttribute("ssnmmode", ErrorMode);
   }
   else
      parmMode = "";

   if  (GoodMode.equals("DELETE"))
     outgoingParms = "mode= " + parmMode;
   else
   {
      outgoingParms = "method=" + parmMethod +
                      "&revised=" + TestRevised[4] +
                      "&mode=" + parmMode;
   }

   outgoingResend = "../TTSpecs/updMethodTTSpecs.jsp?" + outgoingParms;
%>
<%
  response.sendRedirect(outgoingResend);
%>