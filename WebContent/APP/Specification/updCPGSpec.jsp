<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- updCPGSpec.jsp -------------------------------------------//
//
//    Author :  Teri Walton  6/04/02                     
//      moved to Production 12/19/02
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//   1/6/09    TWalton    Changed to point to NEW Stylesheet,  Broke up the page
//                                  Changed to the Standard
//    2/25/04    TWalton    Changed comments and images for 5.0 server.
//   06/02/03    cpaschen   Update with include header/footer 
//   05/20/03    cpaschen   update presentation style     
//   01/10/03    TWalton    Change the Way Test Brix is Done 
//   12/19/02    TWalton    Changed to use new improved files   
//--------------------------------------------------------------------------//

//**************************************************************************//
  String errorPage = "/Specification/updCPGSpec.jsp";
  String updTitle = " Update a CPG Specification";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdSpecification us = new UpdSpecification();
 try
 {
	us = (UpdSpecification) request.getAttribute("updViewBean");
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",updTitle);
     StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlSpecificationNEW?requestType=inqCPGSpec\">Select Again");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************   
%>
<html>
 <head>
   <title><%= updTitle %></title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>   
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
   <%= JavascriptInfo.getCalendarHead() %>  
   <%= JavascriptInfo.getCheckTextareaLength() %>
 </head>
 <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!us.getDisplayMessage().trim().equals(""))
   {
%>      
 <tr class="tr00">
  <td class="td03200102">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= us.getDisplayMessage().trim() %></b></td>
 </tr>    
<%
   }
   if (us.getItemNumber().trim().equals("") ||
       !us.getItemNumberError().trim().equals(""))
   { // ADD Records
%>  
 <tr>
  <td>
   <form name="updateSpec" action="/web/CtlSpecificationNEW?requestType=addCPGSpec" method="post">   
    <table class="table00" cellspacing="0" style="width:100%">
     <tr class="tr02">
        <td class="td0418" style="width:5%">&nbsp;</td>
        <td class="td0418">
         <b>Choose for ADD/Creation of Specification:&nbsp;&nbsp;&nbsp;<font style="color:#990000"><i>All Fields are Required!</i></font>
        </td>
        <td class="td0418" style="text-align:center">
          <%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %>
        </td>
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td>&nbsp;</td>       
        <td class="td04160102"><b><acronym title="Item Number MUST be a VALID M3-Item">Item Number:</acronym></b></td>
        <td class="td03160102">
		  <%= HTMLHelpersInput.inputBoxText("itemNumber", "", "Item Number", 15, 15, "Y", "N") %>&nbsp;<%= us.getItemNumberError() %>
		  <%= HTMLHelpersInput.inputBoxHidden("itemNumberCopy", us.getItemNumberCopy()) %>
		  <%= HTMLHelpersInput.inputBoxHidden("revisionDateCopy", us.getRevisionDateCopy()) %>
        </td>
        <td>&nbsp;</td>      
       </tr>
    </table>
   </form>
  </td>
 </tr>
<%
   }
   else
   { // Update the Records

       
%>
  <tr>
   <td>
    <form name="updateSpec" action="/web/CtlSpecificationNEW?requestType=updCPGSpec" method="post">   
     <table class="table00" cellspacing="0" cellpadding="2">
      <tr>
       <td rowspan="6" style="width:2%">&nbsp;</td>
       <td class="td04160102"><acronym title="Item Number: valid M3-Item Number -- 1 Item for Each Specification (Spec Name is Item Number)">Item Number</acronym></td>
       <%= HTMLHelpersInput.inputBoxHidden("itemNumber", us.getItemNumber()) %>
       <%= HTMLHelpersInput.inputBoxHidden("itemNumberOriginal", us.getItemNumber()) %>
       <td class="td04160102">&nbsp;<b><%= HTMLHelpersLinks.routerItem(us.getItemNumber(), "a0414", "", "") %></b></td>
       <td class="td04140102" style="width:2%">&nbsp;</td>
       <td class="td04140102"><%= us.getBeanSpec().getSpecClass().getItemWhse().getItemDescription() %></td>
       <td rowspan="6" style="width:2%">&nbsp;</td>
      </tr>
<%
  String mouseover = "";
  String dropDownStatus = "";
  try
  {
     Vector ddStatus = us.buildDropDownStatus();
     dropDownStatus = (String) ddStatus.elementAt(0);
     mouseover = (String) ddStatus.elementAt(1);
  }
  catch(Exception e)
  {}
%>      
      <tr>
       <td class="td04140102"><acronym title="<%= mouseover %>">Status of Specification</acronym></td>
       <td class="td04140102">&nbsp;<b><%= dropDownStatus %></b></td>
       <%= HTMLHelpersInput.inputBoxHidden("recordStatusOriginal", us.getRecordStatusOriginal()) %>
       <td class="td04140102" style="width:2%">&nbsp;</td>
       <td class="td04140102" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></td>
      </tr>
      <tr>
       <td class="td04140102"><acronym title="Revision Date for the Specification">Revision Date:</acronym></td>
       <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputBoxDate("revisionDate", us.getRevisionDate(), "getRevisionDate", "N", us.getReadOnly()) %></td>
       <%= HTMLHelpersInput.inputBoxHidden("revisionDateOriginal", us.getRevisionDate()) %>
       <td class="td04140102">&nbsp;</td>
       <td class="td04140102">Supercedes:&nbsp;<%= InqSpecification.formatDateFromyyyymmdd(us.getBeanSpec().getSpecClass().getSupercedesDate()) %></td>
      </tr>	    
      <tr>
       <td class="td04140102"><acronym title="Lab Book ID">Lab Book ID:</acronym></td>
       <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxText("labBookID", us.getLabBookID(), "Lab Book ID", 25, 25, "N", us.getReadOnly()) %></td>
      </tr>	       
      <tr>
       <td class="td04140102"><acronym title="Formula">Formula:</acronym></td>
       <td class="td04140102" colspan = "3">&nbsp;<%= us.buildDropDownFormula() %></td>
      </tr>	      
      <tr>
       <td class="td04140102"><acronym title="Basic Comments - for WHOLE Specification">Additional Comments:</acronym></td>
       <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("comments", us.getComments(), 2, 80, 512, us.getReadOnly()) %></td>
      </tr>	 
<%
   // Create Expandable Section for Bin Type information Relating DIRECTLY to the EMPTY BINS
   String openClose = "O";
//   if (ur.getListBins().size() == 0)
//      openClose = "O";
  int imageCount  = 4;
  int expandCount = 1;
  // Determine if NEW -- If NEW instead of Update then Have Expanding Section come in OPEN
  request.setAttribute("tableType", "test");
%>  
      <tr>
       <td class="td04140102" colspan = "7">
        <table class="table00" cellspacing="0" cellpadding="0">
         <tr class="tr02">
          <td class="td04121405">
           <%= JavascriptInfo.getExpandingSection(openClose, "Analytical Testing", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
           <jsp:include page="updCPGSpecTestProcess.jsp" %></jsp:include>  
           </span>    
          </td>
         </tr>
        </table>   
       </td>   
      </tr>	
<%
  imageCount++;
  expandCount++;
  // Determine if NEW -- If NEW instead of Update then Have Expanding Section come in OPEN
  request.setAttribute("tableType", "process");
%>  
      <tr>
       <td class="td04140102" colspan = "7">
        <table class="table00" cellspacing="0" cellpadding="0">
         <tr class="tr02">
          <td class="td04121405">
           <%= JavascriptInfo.getExpandingSection(openClose, "Process Parameters", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
           <jsp:include page="updCPGSpecTestProcess.jsp" %></jsp:include>  
           </span>    
          </td>
         </tr>
        </table>   
       </td>   
      </tr>	
<%
  imageCount++;
  expandCount++;
  // Determine if NEW -- If NEW instead of Update then Have Expanding Section come in OPEN
%>  
      <tr>
       <td class="td04140102" colspan = "7">
        <table class="table00" cellspacing="0" cellpadding="0">
         <tr class="tr02">
          <td class="td04121405">
           <%= JavascriptInfo.getExpandingSection(openClose, "Additional Packing Instructions", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
           <jsp:include page="updCPGSpecPack.jsp" %></jsp:include>  
           </span>    
          </td>
         </tr>
        </table>   
       </td>   
      </tr>	
     </table>
    </form>
   </td>
  </tr>
  <%= JavascriptInfo.getCalendarFoot("updateSpec", "getRevisionDate", "revisionDate") %>
<%
   }
%>
 </table>
    <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>