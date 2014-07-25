<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.SpecificationNEW" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanSpecification" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listCPGSpecTable.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  6/03/02
//      moved to Production  7/10/02
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//	  10/22/08    TWalton	   Changed to Point to New Box, with New Stylesheet
//								Split off of listCPGSpec								
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqSpecification isTable = new InqSpecification();
 BeanSpecification getData = new BeanSpecification();
 try
 {
	isTable = (InqSpecification) request.getAttribute("inqViewBean");
	getData = (BeanSpecification) isTable.getListReport().elementAt(0);
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewStandard = request.getParameter("displayView");
 if (displayViewStandard == null)
   displayViewStandard = "";

   String columnHeadingTo = "/web/CtlSpecificationNEW?requestType=listCPGSpec&" +
                            isTable.buildParameterResend();
   String[] sortImage = new String[13];
   String[] sortStyle = new String[13];
   String[] sortOrder = new String[13];
   sortOrder[0] = "recordStatus";
   sortOrder[1] = "itemNumber";
   sortOrder[2] = "itemDescription";
   sortOrder[3] = "formulaNumber";
   sortOrder[4] = "shelfLife";
   sortOrder[5] = "revisionDate";
   sortOrder[6] = "supercedesDate";
   
  //************
  //Set Defaults
   for (int x = 0; x < 7; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
   String orderBy = request.getParameter("orderBy");
   if (orderBy == null)
      orderBy = "itemNumber";
   String orderStyle = request.getParameter("orderStyle");
   if (orderStyle == null)
      orderStyle = "";
   for (int x = 0; x < 7; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (orderStyle.equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/UpArrowYellow.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/DownArrowYellow.gif\">";
     }
   }   
%>

<html>
  <head>
     <%= JavascriptInfo.getMoreButton() %>
  </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
  // HEADING SECTION
%>  
   <tr class="tr02">
    <td class="td04120302" style="text-align:center">
     <%= sortImage[0] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
      Spec Status
     </a>      
    </td>
    <td class="td04120302" style="text-align:center">
     <%= sortImage[1] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
      Item Number
     </a>      
    </td>
    <td class="td04120302" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      Description
     </a>      
    </td>      
    <td class="td014CC001" style="border-right:1px solid #CCCC99">
     <%= sortImage[3] %>
     <a class="a0412" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      Formula
     </a>      
    </td>      
    <td class="td04120302" style="text-align:center">
     <b>
      Shelf Life
     </b>      
    </td>   
    <td class="td04120302" style="text-align:center">
    <b>
     Spec Revision Date
     </b>    
    </td>   
<%
   if (0 == 1)
   { // Not going to include FOR NOW
%>    
    <td class="td04120302" style="text-align:center">
     <b>
      Spec Supercedes Date
     </b>      
    </td>   
<%
   }
%>    
    <td class="td04120102" style="width:6%">&nbsp;</td> 
   </tr> 
<%
  // DATA SECTION
  if (getData.getSpecNEW() != null &&
      getData.getSpecNEW().size() > 0)
  {
    String saveitem = "";
    String saverevised = "";
    for (int x = 0; x < getData.getSpecNEW().size(); x++)
    {
      String skipRow = "N";
      SpecificationNEW thisrow = (SpecificationNEW) getData.getSpecNEW().elementAt(x);
      if (saveitem.equals(thisrow.getItemWhse().getItemNumber()) &&
          saverevised.equals(thisrow.getRevisionDate()))
          skipRow = "Y";
      if (skipRow.equals("N"))
      {
%>  
   <tr class="tr00">
    <td class="td04120302">
     <%= thisrow.getSpecStatus() %>
    </td>
    <td class="td04120302">&nbsp;
<%
   if (thisrow.getItemWhse() != null &&
       thisrow.getItemWhse().getItemNumber() != null &&
       !thisrow.getItemWhse().getItemNumber().equals(""))
     out.println(HTMLHelpersLinks.routerItem(thisrow.getItemWhse().getItemNumber(), "a0412", "", ("&revisionDate=" + thisrow.getRevisionDate())));
%>    
    </td> 
    <td class="td04120302">&nbsp;
<%
   if (thisrow.getItemWhse() != null &&
       thisrow.getItemWhse().getItemDescription() != null &&
       !thisrow.getItemWhse().getItemDescription().equals(""))
     out.println(thisrow.getItemWhse().getItemDescription());
%>        
    </td> 
    <td class="td04120302">&nbsp;
<%
   if (thisrow.getFormulaNumber() != null &&
       !thisrow.getFormulaNumber().equals(""))
     out.println(HTMLHelpersLinks.routerFormula(thisrow.getFormulaNumber(), "", "a0412", "", ""));
   // Take out the link to the Router
%>    
    </td>   
    <td class="td04120302">
     &nbsp;
<%
   if (thisrow.getItemWhse().getDaysShelfLife() != null)
      out.println(thisrow.getItemWhse().getDaysShelfLife() + " Days");
   else
      out.println("None Found");
%>     
    </td> 
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getRevisionDate() %>
    </td>  
<%
   if (0 == 1)
   { // Not going to Display for now
%>    
    <td class="td04120302" style="text-align:center">
     &nbsp;<%= thisrow.getSupercedesDate() %>
    </td>  
<%
   }
%>    
    <td class="td04120102" style="text-align:right">
     <%= InqSpecification.buildMoreButton(isTable.getRequestType(), thisrow.getItemWhse().getItemNumber(), thisrow.getRevisionDate(), thisrow.getSpecStatus(), isTable.getAllowUpdate(), isTable.buildParameterResend()) %>
    <td>
   </tr>   
<%  
       }
       saveitem = thisrow.getItemWhse().getItemNumber();
       saverevised = thisrow.getRevisionDate();
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
</html>