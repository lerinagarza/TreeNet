<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*, java.io.*, javax.sql.*"%>
<%@ page import="com.treetop.*" %>
<%@ page import="com.ibm.as400.access.*" %>
<%
//------------------ upd2FormulaTTSpecs.jsp ------------------------------//
//
// Author :  Teri Walton  9/16/02                                         
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
   String urlAddress = "/web/JSP/TTSpecs/updFormulaTTSpecs.jsp(USE)";
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

   ssn.removeAttribute("ssnfnote");
   ssn.removeAttribute("ssnfname");
   ssn.removeAttribute("ssnfstatus");
   ssn.removeAttribute("ssnfdetail");
   ssn.removeAttribute("ssnfcoment");
   ssn.removeAttribute("ssnfmode");

//***  Incoming Parameter Variables  ***

   String parmFormula = request.getParameter("formula");
   if  (parmFormula == null || parmFormula.equals("") || parmFormula.equals(" "))
   {
      parmFormula = " ";
      ssn.setAttribute("ssnfnote", "ERROR  --  No Formula Entered.  Please enter a Formula Number.");
      ErrorMode = "ERFORM";
   }

   String parmRevised = request.getParameter("revised");
   String TestRevised[] = CheckDate.validateDate(parmRevised);
   if  (parmRevised == null || parmRevised.equals(""))
   {
      parmRevised = " ";
      ssn.setAttribute("ssnfnote", "ERROR  --  No Revision Date Entered.  Please enter a Revision Date.");
      ErrorMode = "ERREVIS";
   }
   else
   {
      if  (TestRevised[0].equals(""))
      {
         ssn.setAttribute("ssnfnote", "ERROR  --  " + TestRevised[6]);
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

   String parmfName = request.getParameter("fname");
   if  (parmfName == null)
      parmfName = " ";

   String parmfDetail = request.getParameter("fdetail");
   if  (parmfDetail == null)
      parmfDetail = " ";

   String parmfStatus = request.getParameter("fstatus");
   if  (parmfStatus == null)
      parmfStatus = " ";

   String parmfComent = request.getParameter("fcoment");
   if  (parmfComent == null)
      parmfComent = " ";

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
//   ADD New Formula
//******************************************************************************

      if  (parmMode.equals("add") || parmMode.equals("addnew"))
      {
         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLLFRM1 " +
                                 "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 "AND SPLRECST = 'PENDING   ' " +
                                 "ORDER BY SPLNAME");
            if (rs.next())
            {
               //*****
               // Record Already Exists in the File
               //*****
               ssn.setAttribute("ssnfnote", "ERROR  --  Cannot add pending Formula " + parmFormula + "Revised " + TestRevised[1] + ", already there.");
               ErrorMode = "ERRADD";
            }
            else
            {
	        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO dbprd.SPLLFRM1 " +
	                          "(SPLFORM, SPLNAME, SPLREVIS, SPLDETAIL, SPLSTATUS, " +
                                 "SPLCOMENT, SPLALUP, SPLRECST, SPLPROG, " +
                                 "SPLDATE, SPLTIME, SPLUSER, SPLWSN) " +
                                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

               pstmt.setString(1, parmFormula);
	        pstmt.setString(2, parmfName);
	        pstmt.setInt(3, revDateNum);
	        pstmt.setString(4, parmfDetail);
	        pstmt.setString(5, parmfStatus);
	        pstmt.setString(6, parmfComent);
	        pstmt.setString(7, "YES");
	        pstmt.setString(8, "PENDING   ");
	        pstmt.setString(9, "upd2FormulaTTSpecs.jsp");
               pstmt.setInt(10, saveDateInt);
               pstmt.setInt(11, saveTimeInt);
               pstmt.setString(12, myProfile);
	        pstmt.setString(13, " ");

	        pstmt.executeUpdate();

               ssn.setAttribute("ssnfnote", "Thank you! Formula " + parmFormula + "Revised " + TestRevised[1] + " has been added.");
               GoodMode = "ADD";
            }
            rs.close();
            stmt.close();
         } catch (SQLException e)
           {   }

      }

//******************************************************************************
//   ADD New Revision of a Formula (Default in Today's Date as Revision Date)
//******************************************************************************

      if  (parmMode.equals("rev"))
      {
         //*****
         //   Copy Record from File and Insert another Record into the Same File
         //*****
         try
         {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLLFRM1 " +
                                 "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 "AND SPLREVIS = " + TestRevised[4] + " " +
                                 "ORDER BY SPLNAME");
            if (rs.next())
            {
	        PreparedStatement pstmt7 = conn.prepareStatement("INSERT INTO dbprd.SPLLFRM1 " +
	                          "(SPLFORM, SPLNAME, SPLREVIS, SPLDETAIL, SPLSTATUS, " +
                                 "SPLCOMENT, SPLALUP, SPLRECST, SPLPROG, " +
                                 "SPLDATE, SPLTIME, SPLUSER, SPLWSN) " +
                                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

               pstmt7.setString(1, parmFormula);
	        pstmt7.setString(2, rs.getString("SPLNAME"));
	        pstmt7.setInt(3, saveDateInt);
	        pstmt7.setString(4, rs.getString("SPLDETAIL"));
	        pstmt7.setString(5, rs.getString("SPLSTATUS"));
	        pstmt7.setString(6, rs.getString("SPLCOMENT"));
	        pstmt7.setString(7, "YES");
	        pstmt7.setString(8, "PENDING   ");
	        pstmt7.setString(9, "upd2FormulaTTSpecs.jsp");
               pstmt7.setInt(10, saveDateInt);
               pstmt7.setInt(11, saveTimeInt);
               pstmt7.setString(12, myProfile);
	        pstmt7.setString(13, " ");

	        pstmt7.executeUpdate();

               ssn.setAttribute("ssnfnote", "Thank you! Formula " + parmFormula + "NEW Revision " + saveDate + " has been added.");
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbprd.SPLLFRM1 " +
                     "WHERE SPLFORM = \'" + parmFormula + "\' " +
                     "AND SPLRECST <> 'PENDING   ' " +
                     "ORDER BY SPLREVIS DESC");
            if (rs.next())
            {
               saveRevisEdit = rs.getString("SPNREVIS");
               if  (revDateNum <= rs.getInt("SPNREVIS"))
               {
                  ssn.setAttribute("ssnfnote", "Revision Date" + TestRevised[1] + " for " + parmFormula +
                                   " is Less Than or Equal to Active Formula Date " + saveRevisEdit + ".");
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
               //       Only one active Record per Formula.
               //*****
    	        PreparedStatement pstmt7 = conn.prepareStatement("UPDATE dbprd.SPLLFRM1 " +
    	                       "SET SPLRECST = ?, SPLPROG = ?, SPLDATE = ?, SPLTIME = ?, " +
	                       "SPLUSER = ? " +
                                 "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 "AND SPLRECST = 'ACTIVE    ' ");

               pstmt7.setString(1, "INACTIVE  ");
  	        pstmt7.setString(2, "upd2FormulaTTSpecs.jsp");
               pstmt7.setInt(3, saveDateInt);
               pstmt7.setInt(4, saveTimeInt);
               pstmt7.setString(5, myProfile);

	        pstmt7.executeUpdate();

               //*****
               //   Update Pending Record to Active Status.
               //*****
	        PreparedStatement pstmt2 = conn.prepareStatement("UPDATE dbprd.SPLLFRM1 " +
	                       "SET SPLRECST = ?, SPLPROG = ?, SPLDATE = ?, SPLTIME = ?, " +
	                       "SPLUSER = ? " +
                                 "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 "AND SPLRECST = 'PENDING    ' ");

               pstmt2.setString(1, "ACTIVE    ");
      	        pstmt2.setString(2, "upd2FormulaTTSpecs.jsp");
               pstmt2.setInt(3, saveDateInt);
               pstmt2.setInt(4, saveTimeInt);
               pstmt2.setString(5, myProfile);

	        pstmt2.executeUpdate();

               ssn.setAttribute("ssnfnote", "Thank you! Formula " + parmFormula + "Revised " + TestRevised[1] + " has been Approved.");
               GoodMode = "UPDATE";
            }
            else
            {
               //*****
               // Update Record
               //*****
	        PreparedStatement pstmt1 = conn.prepareStatement("UPDATE dbprd.SPLLFRM1 " +
	                       "SET SPLNAME = ?, SPLREVIS = ?, SPLDETAIL = ?, " +
	                       "SPLSTATUS = ?, SPLCOMENT = ?, " +
	                       "SPLPROG = ?, SPLDATE = ?, SPLTIME = ?, SPLUSER = ? " +
                                 "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 "AND SPLRECST = 'PENDING   ' ");

               pstmt1.setString(1, parmfName.trim());
               pstmt1.setString(2, TestRevised[4]);
               pstmt1.setString(3, parmfDetail.trim());
               pstmt1.setString(4, parmfStatus);
               pstmt1.setString(5, parmfComent.trim());
               pstmt1.setString(6, "upd2FormulaTTSpecs.jsp");
               pstmt1.setInt(7, saveDateInt);
               pstmt1.setInt(8, saveTimeInt);
               pstmt1.setString(9, myProfile);

	        pstmt1.executeUpdate();

               ssn.setAttribute("ssnfnote", "Thank you! Formula " + parmFormula + "Revised " + TestRevised[1] + " has been updated.");
               GoodMode = "UPDATE";
            }
         }
      }

//******************************************************************************
//   INACTIVATE      Because there should only be one active record.
//                 Change all Active Records with this Formula to Inactive
//******************************************************************************

      if  (parmMode.equals("inactivate"))
      {
         PreparedStatement pstmt4 = conn.prepareStatement("UPDATE dbprd.SPLLFRM1 " +
                          "SET SPLRECST = ?, SPLPROG = ?, SPLDATE = ?, SPLTIME = ?, " +
                          "SPLUSER = ? " +
                                 "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                 "AND SPLRECST = 'ACTIVE    ' ");

         pstmt4.setString(1, "INACTIVE  ");
         pstmt4.setString(2, "upd2FormulaTTSpecs.jsp");
         pstmt4.setInt(3, saveDateInt);
         pstmt4.setInt(4, saveTimeInt);
         pstmt4.setString(5, myProfile);

	  pstmt4.executeUpdate();

         ssn.setAttribute("ssnfnote", "Thank you! Formula " + parmFormula + " has been Inactivated.");
         GoodMode = "UPDATE";
      }

//******************************************************************************
//   DELETE Pending Record from File
//******************************************************************************

      if  (parmMode.equals("delete"))
      {
         PreparedStatement pstmtdel = conn.prepareStatement("DELETE FROM dbprd.SPLLFRM1 " +
                                  "WHERE SPLFORM = \'" + parmFormula + "\' " +
                                  "AND SPLRECST = 'PENDING   ' ");

         pstmtdel.executeUpdate();

         ssn.setAttribute("ssnfnote", "Thank you! Pending Formula " + parmFormula + "Revised " + TestRevised[1] + " has been deleted.");
         GoodMode = "DELETE";
      }

//******************************************************************************
   }

   if  (!ErrorMode.equals(" "))
   {
      ssn.setAttribute("ssnfname", parmfName);
      ssn.setAttribute("ssnfstatus", parmfStatus);
      ssn.setAttribute("ssnfdetail", parmfDetail);
      ssn.setAttribute("ssnfcoment", parmfComent);
      ssn.setAttribute("ssnfmode", ErrorMode);
   }
   else
      parmMode = "";

   if  (GoodMode.equals("DELETE"))
     outgoingParms = "mode= " + parmMode;
   else
   {
      outgoingParms = "formula=" + parmFormula +
                      "&revised=" + TestRevised[4] +
                      "&mode=" + parmMode;
   }

   outgoingResend = "../TTSpecs/updFormulaTTSpecs.jsp?" + outgoingParms;
%>

<%
   response.sendRedirect(outgoingResend);

%>