<%@ page language="java" %>
<%@ page import = "com.treetop.servlets.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "java.net.URLEncoder" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%
//---------------- inqSpecifications.jsp -----------------------//
//      Change Name from inqIngSpecs.jsp
//   Author :  Charlena Paschen  04/25/02
//   Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  3/21/05   TWalton      Changed Name, changed from JSP to Servlet Driven
//  3/09/04   TWalton      Changed comments and images for 5.0 server.
//------------------------------------------------------------//

  String inquiryTitle = "Specification Inquiry";  
//**************************************************************************//
   request.setAttribute("title",inquiryTitle);
//*****************************************************************************
%>
<html>
 <head>
        <title><%= inquiryTitle %></title>
    <%= JavascriptInfo.getClickButtonOnlyOnce() %>
    <%= JavascriptInfo.getChangeSubmitButton() %>   
    <%= JavascriptInfo.getExpandingSectionHead("Y", 2, "", 0) %>    

 </head>
 <body>
 <jsp:include page="../include/heading.jsp"></jsp:include>
   
<table class="table01001" cellspacing="0" style="width:100%">
 <tr>
  <td style="width:3%">&nbsp;</td>
  <td>
   <table class="table01001" cellspacing="0" style="width:100%">
    <form action="/web/CtlSpecification" method="post">
      <%= HTMLHelpersInput.inputBoxHidden("requestType", "list") %>
    <tr class="tr02001">
      <td class="td041CR001" style="width:3%">&nbsp;
      </td>
      <td class="td061CL001" colspan = "2">
          <b>Search On:</b>
      </td>
      <td class="td061CR001"colspan = "2"><b>and then press</b>
        <%= HTMLHelpers.buttonGo("") %>
      </td>  
      <td class="td041CR001" style="width:3%">&nbsp;
      </td> 
    </tr>
    <tr class="tr00001">
      <td class="td041CR002" rowspan="15">&nbsp;
      </td>
      <td class="td041CR002" style = "width:1%">&nbsp;
      </td>
      <td class="td044CL002">
        Specification Type
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String specType = request.getParameter("inqspectype");
 if (specType == null)
   specType = "";  
 String dropDownType = "";
try
{   
		Vector typeList    = GeneralInfo.findDescByFull("ACU");
		dropDownType       = GeneralInfo.buildDropDownFullForKey1(
								typeList, specType,
								"inqspectype", "All");
}
catch(Exception e)
{}
%>      
         <%= dropDownType %>
      </td>
      <td class="td041CR002" style = "width:1%">&nbsp;
      </td>
      <td class="td041CR002" rowspan="15">&nbsp;
      </td> 
    </tr> 
    <tr class="tr00001">
      <td class="td041CR002" style = "width:1%">&nbsp;
      </td>
      <td class="td044CL002">
        Specification
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String specification = request.getParameter("inqspecification");
 if (specification == null)
   specification = "";  
%>      
         <%= HTMLHelpersInput.inputBoxText("inqspecification", specification, "Spec ID", 11, 10, "N", "N") %>
      </td>
      <td class="td041CR002" style = "width:1%">&nbsp;
      </td>
    </tr> 
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;
      </td>
      <td class="td044CL002">
        Spec Description
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String specdesc = request.getParameter("inqspecdesc");
 if (specdesc == null)
   specdesc = "";  
%>      
         <%= HTMLHelpersInput.inputBoxText("inqspecdesc", specdesc, "Spec Description", 30, 30, "N", "N") %>
      </td>
      <td class="td041CR002">&nbsp;
      </td>
    </tr>     
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Item
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String resource = request.getParameter("inqresource");
 if (resource == null)
   resource = ""; 
   //Specification.buildDropDownResource(resource, "inqresource", "All") 01/07/09 - wth
%>      
         <%= InqSpecification.buildDropDownItemInqIngSpec()  %>
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>

    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Customer
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String customer = request.getParameter("inqcustomer");
 if (customer == null)
   customer = "";      
%>   
        <%= InqSpecification.buildDropDownCustomerInqIngSpec() %>
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr> 
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Fruit Variety
      </td>
      <td class="td044CL002">&nbsp;
<%
 String variety = request.getParameter("inqvariety");
 if (variety == null)
   variety = "";  

  Vector doNotInclude = new Vector();
  String varietyDD = "";
  try
  {
    Vector thisList = GeneralInfoDried.findDescByCode("FV");
    varietyDD   = GeneralInfoDried.buildDropDownFullForCode(thisList, doNotInclude, variety, "inqvariety", "Variety");
  }
  catch(Exception e)
  {
  }
%>                          
          <%= varietyDD %>  
        &nbsp;&nbsp;and&nbsp;    
<%
   String incSel = "";
   String excSel = "";
   String priSel = "";
   String fvList = request.getParameter("inqFVList");
   if (fvList != null &&
       !fvList.equals("") &&
       !fvList.equals("All"))
   {
	  if (fvList.equals("Included"))
	    incSel = "selected"; 
	  if (fvList.equals("Excluded"))
	    excSel = "selected"; 
	  if (fvList.equals("Primary"))
	    priSel = "selected"; 	    	    
   }    
%>        
        <select name="inqFVList">
          <option value="All"> All
          <option value="Included" <%= incSel %>> Included&nbsp;
          <option value="Excluded" <%= excSel %>> Excluded
          <option value="Primary" <%= priSel %>> Primary
        </select>               
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>  
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Specification Status
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String ssclosed   = "";
 String ssopen  = "";
 String specstatus = request.getParameter("inqspecstatus");
 if (specstatus == null)
   specstatus = ""; 
   
 if (specstatus.equals("Closed"))
   ssclosed = " selected";
 if (specstatus.equals("Open"))
   ssopen = " selected"; 
%>      
        <select name="inqspecstatus">
          <option value="All"> All
          <option value="Closed" <%= ssclosed %>> Closed
          <option value="Open" <%= ssopen %>> Open
        </select>   
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>  
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Customer Status
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String csactive   = "";
 String csdeleted  = "";
 String csinactive = ""; 
 String custstatus = request.getParameter("inqcuststatus");
 if (custstatus == null)
   custstatus = "";
 else
 {
    if (custstatus.equals("Active"))
       csactive = " selected";
    if (custstatus.equals("Deleted"))
       csdeleted = " selected";
    if (custstatus.equals("Inactive"))
       csinactive = " selected";       
 }
%>       
        <select name="inqcuststatus">
          <option value="All"> All
          <option value="Active" <%= csactive %>> Active
          <option value="Deleted" <%= csdeleted %>> Deleted
          <option value="Inactive" <%= csinactive %>> Inactive
        </select>         
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>   
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Specification Revisions
      </td>
      <td class="td044CL002">&nbsp;
<%= CtlSpecification.buildShowRevisionsDropDown(request.getParameter("showrevisions")) %>

      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>        

    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Show Customer Information?
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String showcustomer = request.getParameter("showcustomer");
 if (showcustomer == null ||
     showcustomer.equals("N"))
   showcustomer = "N";
 else
   showcustomer = "Y";  
%>      
        <%= HTMLHelpersInput.inputCheckBox("showcustomer", showcustomer, "N") %> 
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>              
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CL002">
        Show Variety Information?
      </td>
      <td class="td044CL002">&nbsp;
<%      
 String showvariety = request.getParameter("showvariety");
 if (showvariety == null ||
     showvariety.equals("N"))
   showvariety = "N";                 
 else
   showvariety = "Y";     
%>   
      
        <%= HTMLHelpersInput.inputCheckBox("showvariety", showvariety, "N") %> 
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>  
    
    <tr class="tr00001">
      <td class="td041CR002">&nbsp;</td>    
      <td class="td044CC002" colspan = "2">
        <%= HTMLHelpers.buttonGo("") %>
      </td>
      <td class="td041CR002">&nbsp;</td>      
    </tr>  
<%   
//  if (1 == 0)
//  { 
   //****************************
   // Analytical Section
%>    
    <tr class="tr02001"> 
      <td colspan = "7">
        <%= JavascriptInfo.getExpandingSection("O", "Analytical Section", 12, 1, 3, 1, 0) %>
    <table class="table00001" cellspacing="0" style="width:100%">
    <tr class="tr01001">
    <tr>
      <td class="td041CR002" style="width:5%" rowspan="7">&nbsp;
      </td>
      <td class="td041CR002" style = "width:1%">&nbsp;
      </td>    
      <td class="td044CL002" style="width:35%">
<%//        <a href="/web/CtlAttributes?requestType=list&summaryLevel=showValues"
   //        title="Look at All Attributes and Values with descriptions, images and documents."
    //       target="_blank">
     //     ANALYTICAL CODE (Click to see all codes)
      //  </a>
      %>
      </td>
<%

//      <td class="td041CL002" style="width:25%">
  //       <b>VALUE FROM</b>
    //  </td>
//      <td class="td041CL002" style="width:25%">
  //       <b>VALUE TO</b>
    //  </td>
%>      
      <td class="td041CR002" style = "width:1%">&nbsp;
      </td>      
      <td class="td041CR002" style="width:5%" rowspan="7">&nbsp;
      </td>
    </tr>  
<%
  Vector fieldNames = new Vector();
   for (int x = 1; x < 6; x++)
   {
      String codeName  = "inqAnalyticalCode" + x;
      fieldNames.addElement(codeName);
   }   
   Vector ddLists = SpecificationAnalytical.buildDropDownAttribute("", 
       															   fieldNames,
       															   "Select a Code");
   for (int x = 0; x < 5; x++)
   {
//    String fromValue = "inqFromValue" + x;
//    String toValue   = "inqToValue" + x;
%>
    <tr class="tr00001">
      <td class="td044CL002">&nbsp;
      </td>
     <td class="td044CL002" style="width:35%">
        <b><%= (String) ddLists.elementAt(x) %></b>
      </td>      
<%
//      <td class="td044CL002">&nbsp;
  //        <%= HTMLHelpersInput.inputBoxNumber(fromValue, 
    //        	                         "",
      //          	                     "",
        //            	                  10,
          //              	              11,
            //                	         "N", "N") 
//      </td>
  //    <td class="td044CL002">&nbsp;
    //      <%= HTMLHelpersInput.inputBoxNumber(toValue, 
      //      	                         "",
        //        	                     "",
          //          	                  10,
            //            	              11,
              //              	         "N", "N")
//      </td>      
%>      
      <td class="td041CR002">&nbsp;</td>           
    </tr>   
<%
   }
%>   
      <td class="td041CR002">&nbsp;
      </td>    
      <td class="td044CC002">
        <%= HTMLHelpers.buttonGo("") %>
      </td>
      <td class="td041CR002">&nbsp;
      </td>                     
    </table>
    </span>
    </td>
    </tr>       
<%
//   }
%>                                                                                         
  </form>
  </table>
  </td>
  <td style="width:3%">
  </td>
 </tr> 
</table>  
<jsp:include page="../include/footer.jsp"></jsp:include>
   </body>
</html>