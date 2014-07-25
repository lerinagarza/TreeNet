<%@ page language = "java" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.data.Counter" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------------------------------------------------------------->
//--                                                                         -->
//--   Author : Thaile      Date:                                            -->
//--   CHANGES:                                                              -->
//--                                                                         -->
//--      Date    Name  Comments                                             -->
//--   --------   ----  --------                                             -->
//--   11/2/08    Twalton  Change Stylesheet - point to NEW Box              -->
//--   06/25/04   WTH   Allow for new Counter class to replace counter bean  -->
//----------------------------------------------------------------------------->
//****************************************************************************//
   // Obtain the incoming parameters for use in counterBean access.

   String thelongurl = request.getParameter("theurl");
   int x = thelongurl.indexOf("?");
   if(x != -1)
	thelongurl = thelongurl.substring(0,x);

   String thedate = request.getParameter("thedate");
   String theuser = request.getParameter("theuser");
   String thecount = request.getParameter("thecount");
   String theurlnumber = request.getParameter("theurlnumber");
   
   String thisTitle = "Summary of Times Visited";
//**************************************************************************//
  // Allows the Title to display in the Heading Area of the Page
   request.setAttribute("title",thisTitle);
//*****************************************************************************      
%>

<html>
 <head>
   <title>Summary of Times Visited</title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 3, "Y", 3) %>   
   </head>
 <body>

 <jsp:include page="../Include/heading.jsp"></jsp:include>

<table class="table00" cellspacing="0" cellpadding="3">
 <tr>
  <td style="width:1%">&nbsp;</td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr>
     <td class="td0414" style="width:2%">&nbsp;</td>
	 <td class="td0520"><b>Web Page: &nbsp;&nbsp;&nbsp;Visted&nbsp;<%= thecount %>&nbsp;Times</b></td>
	 <td class="td0414" style="width:2%">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td0414">&nbsp;</td>
	 <td class="td0416">&nbsp;&nbsp;&nbsp;<%= thelongurl %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
   </table>
  </td>       
  <td style="width:1%">&nbsp;</td>
 </tr>    
 <tr>
  <td></td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("O", "Visits by Date", 0, 1, 3, 1, 0) %>
       <table class="table00" cellspacing="0">
        <tr class="tr02">
         <td class="td04140102" style="width:3%">&nbsp;</td>
         <td class="td04140102"><b>Day</b></td>
         <td class="td04140102"><b>Date</b></td>
         <td class="td04140102"><b>Times Visited</b></td>
         <td class="td04120102" style="width:3%">&nbsp;</td>
        </tr>
<%
    ResultSet rsCB2 = null;
    try
    {
	   rsCB2 = Counter.findCountByDate(thelongurl);
       while (rsCB2.next())
       {
            DateTime dt = UtilityDateTime.getDateFromyyyyMMddWithDash(rsCB2.getDate("DP2DTE") + "");
%>

        <tr class="tr00001">
         <td class="td04120102">&nbsp;</td>
         <td class="td04120102"><%= dt.getDayOfWeek() %></td>
         <td class="td04120102"><%= dt.getDateFormatMMddyyyySlash() %></td>
         <td class="td04120102"><%= rsCB2.getString("hits") %></td>
         <td class="td04120102">&nbsp;</td>
        </tr> 
<%     
      }
     }
     catch(Exception e) {
      out.println("Error Found: " + e);
     }
     finally
     {
        try
        {
           rsCB2.close();
        }
        catch(Exception e)
        {}
     }
%>
       </table>
      </span>    
     </td>
    </tr>
   </table>
  </td>
 </tr>  
 <tr>
  <td></td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("O", "Visits and Last Date Visited By User", 0, 2, 4, 1, 0) %>
       <table class="table00" cellspacing="0">
        <tr class="tr02">
         <td class="td04140102" style="width:3%">&nbsp;</td>
         <td class="td04140102"><b>User Name</b></td>
         <td class="td04140102"><b>Times Visited</b></td>
         <td class="td04140102"><b>Last Day Visited</b></td>
         <td class="td04140102"><b>Last Date Visited</b></td>
         <td class="td04120102" style="width:3%">&nbsp;</td>
        </tr>
 <%
    ResultSet rsCB = null;
    String profile = "";
    int visitedCount = 0;
    String longName = "";
    String showDay = "";
    String showDate = "";
    try
    {
	  // accumulate total hits by user for presentation.
	  rsCB = Counter.findCountByUser(thelongurl);
	  int    firstRecord = 0;
	  
	  while (rsCB.next())
	  {
	      if (!profile.equals(rsCB.getString("DP2USR")))
	      {
	         if (firstRecord != 0)
	         {

%>			
        <tr class="tr00001">
         <td class="td04120102">&nbsp;</td>
         <td class="td04120102"><%= longName %></td>
         <td class="td04120102"><%= visitedCount %></td>
         <td class="td04120102"><%= showDay %></td>
         <td class="td04120102"><%= showDate %></td>
         <td class="td04120102">&nbsp;</td>
        </tr> 		      
<%	      
	          }      
	       // Set the profile
	         profile = rsCB.getString("DP2USR");
	       // Set the LONG Name
	         longName = "No long name found(" + rsCB.getString("DP2USR") + ")";
	         if (rsCB.getString("DPNUSRNAME") != null &&
	             !rsCB.getString("DPNUSRNAME").trim().equals(""))
	            longName = rsCB.getString("DPNUSRNAME");
	         if (profile.trim().toUpperCase().equals("DEFAULT"))
		     { // Get Grower Information for the Long Name
		     
		 //  String secSys    	= rsCB.getString("DP2SECSYS").trim();
//		  String secVal     = rsCB.getString("DP2SECVAL").trim();
//		  String secUsr     = rsCB.getString("DP2SECUSR").trim();     
		     
		     
	//		 if(secSys.equals("GR"))
	//		 {
	//			try{
	//				Integer grwNbr = new Integer(secVal);
	//				Grower grower = new Grower(grwNbr);
       //         	longName = grower.getGrowerName();
      //          } catch (Exception e) {
     //           	longName = "grower nbr not found";
    //            }
   //          }
               
  //   		 if(secSys.equals("FM"))
 //            {
//             	String formValue = "";
        //     	try {
       //         	formValue = secVal.trim();
      //              Integer f3 = new Integer(formValue.substring(2, formValue.length() ));
     //               longName = FormData.findDataByLogonUser(f3, secUsr);
    //            } catch (Exception e) {
   //             	longName = formValue;
  //              }
 //           } 
             }		            
	         
	         visitedCount = 0;
	      } // end if NEW User
	      visitedCount = visitedCount + rsCB.getInt("DP2HIT");
	//      System.out.println("hits:" + rsCB.getInt("DP2HIT"));
	      	           firstRecord++;
	      try
	      {
	         DateTime dt = UtilityDateTime.getDateFromyyyyMMddWithDash(rsCB.getDate("DP2DTE") + "");
 	         showDay = dt.getDayOfWeek();
 	         showDate = dt.getDateFormatMMddyyyySlash();
 	      }
 	      catch(Exception e)
 	      {
// 	         System.out.println("STOP" + e);
 	      }
         }       
       }
       catch(Exception e) {
         out.println("Error Found: " + e);
       }
       finally
       {
          try
          {
            rsCB.close();
          }
          catch(Exception e)
          {}
       }
       if (!profile.trim().equals(""))
       {
%>

        <tr class="tr00001">
         <td class="td04120102">&nbsp;</td>
         <td class="td04120102"><%= longName %></td>
         <td class="td04120102"><%= visitedCount %></td>
         <td class="td04120102"><%= showDay %></td>
         <td class="td04120102"><%= showDate %></td>
         <td class="td04120102">&nbsp;</td>
        </tr> 	
<%	      
         }
%>       
       </table>
      </span>    
     </td>
    </tr>
   </table>
  </td>
  </tr>  
</table>

<%@ include file="../Include/footer.jsp" %>
</body>
</html>