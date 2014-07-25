<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------  dtlGTINFamilyTree.jsp  ------------------------------------------//
//   To Be included in the dtlGTIN Page / And Other Pages
//
//   Author :  Teri Walton  10/14/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//   5/7/08     TWalton	   Changed to use the NEW Stylesheet
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
  String errorPageTree = "/GTIN/dtlGTINFamilyTree.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 Vector   familyTree = new Vector();
 int      familySize = 0;
 String   rType = (String) request.getAttribute("reqType");
 if (rType == null)
    rType = "";
 String   gtinOriginal = (String) request.getAttribute("gtin");
 if (gtinOriginal == null)
    gtinOriginal = "";
    
 try
 {
	familyTree = (Vector) request.getAttribute("familyTree");
	familySize = familyTree.size();
 }
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }   
%>

<% 
 if (familySize > 0)
 {
%> 
 <table class="styled">
<%
  for (int fam = 0; fam < familySize; fam++)
  {
    BeanGTIN family     = (BeanGTIN) familyTree.elementAt(fam);
    if (family.getGtin() != null &&
        family.getGtin().getRecordID() != null)
    {    
%>
  <tr>
   <th>
      <b><%= family.getGtin().getTradeItemUnitDescriptor() %></b>
<%
// OLD     if (family.getResource() != null &&
// OLD        family.getResource().getResourceNumber() != null)
// OLD         out.println("&nbsp;&nbsp;" + HTMLHelpersLinks.routerResource(family.getResource().getResourceNumber(), "a04002", "", ""));
      if (family.getItem() != null &&
          family.getItem().getItemNumber() != null)
          out.println("&nbsp;&nbsp;" + HTMLHelpersLinks.routerItem(family.getItem().getItemNumber(), "a0414", "", ""));
          
%>      
   </th>
   <th>
      <b><%= family.getGtin().getGtinLongDescription() %></b>
   </th>
   <th class="left">
      <%= HTMLHelpersLinks.routerGTIN(family.getGtin().getGtinNumber(), "a02001", "", "") %>
   </th>
   <th>
      &nbsp;
   </th>
   <th>
<%
   if (family.getGtin().getIsBaseUnit() != null &&
       family.getGtin().getIsBaseUnit().equals("T"))
     out.println("<b>Base Unit</b>");
   else
     out.println("&nbsp;");
%>   
   </th>
   <th>
<%
   if (family.getGtin().getPublishToUCCNet() != null &&
       family.getGtin().getPublishToUCCNet().equals("Y"))
     out.println("<b>Load Yes</b>");
   else
   {
     if (family.getGtin().getPublishToUCCNet() != null &&
         family.getGtin().getPublishToUCCNet().equals("N"))
       out.println("<b>Load No</b>");
     else
       out.println("<b>Pending</b>");
   }  
%>   
   </th>
<%     
	if (rType.equals("update"))
	{   
%>	
   <th>
     <%--  <%= UpdTieToChildren.buildMoreButton("familyTree", family.getGtin().getGtinNumber(), family.getGtin().getGtinLongDescription()) %> --%>
      <input type="button" value="More" class="moreButton" />
		<ul class="tooltip right">
			<li><a target="_blank" href="/web/CtlGTIN?requestType=detail&gtinNumber=<%= family.getGtin().getGtinNumber() %>">Details of <%= family.getGtin().getGtinNumber() %></a></li>
			<li><a target="_blank" href="/web/CtlGTIN?requestType=update&gtinNumber=<%= family.getGtin().getGtinNumber() %>">Update <%= family.getGtin().getGtinNumber() %></a></li>
			<li><a target="_blank" href="/web/CtlGTIN?requestType=updateTies&parentGTIN=<%= family.getGtin().getGtinNumber() %>">Maintain Children of <%= family.getGtin().getGtinNumber() %></a></li>
		</ul>
   </th>   
<%
     }
%>   
  </tr>
<%
  if (family.getChildren() != null &&
      family.getChildren().size() > 0)
  {  
   for (int child = 0; child < family.getChildren().size(); child++)
  {
     GTINChild thisChild = (GTINChild) family.getChildren().elementAt(child);
     if (thisChild.getGtinNumber() != null)
     {
      GTINView thisView = (GTINView) family.getViews().elementAt(child);
      boolean isCurrentGTIN = false;
      if (thisChild.getGtinNumber().equals(gtinOriginal)) {
         isCurrentGTIN = true;
      }

%>  
  <tr <%if(isCurrentGTIN){ %>class="bold"<%} %>>
   <td>
<%
  for (int level = 0; level < (new Integer(thisView.getViewLevel())).intValue(); level++)
  {
     out.println("<b>..</b>");
  }
  if (thisChild.getGtinNumber().equals(gtinOriginal))
    out.println("<b>");  
  if (thisChild.getTradeItemUnitDescriptor() != null)
    out.println(thisChild.getTradeItemUnitDescriptor());
  else
    out.println("N/A");
  if (thisChild.getGtinNumber().equals(gtinOriginal))
    out.println("</b>"); 
  if (family.getChildItems() != null &&
      (com.treetop.businessobjects.Item) family.getChildItems().elementAt(child) != null &&
      ((com.treetop.businessobjects.Item) family.getChildItems().elementAt(child)).getItemNumber() != null)
          out.println("&nbsp;&nbsp;" + HTMLHelpersLinks.routerItem(((com.treetop.businessobjects.Item) family.getChildItems().elementAt(child)).getItemNumber(), "a0412", "", ""));
%>
   </td>  
   <td>
<%
  if (thisChild.getGtinNumber().equals(gtinOriginal))
    out.println("<b>");  
  if (thisView.getGtinLongDescription() != null)
    out.println(thisChild.getGtinLongDescription());
  if (thisChild.getGtinNumber().equals(gtinOriginal))
    out.println("</b>");  
%>   
	 &nbsp;
   </td>    
   <td>
      <%= HTMLHelpersLinks.routerGTIN(thisChild.getGtinNumber(), "a02001", "", "") %>
<%
   if (thisChild.getIsOrderableUnit().trim().equals("T"))
      out.println("  Orderable Unit");
%>      
   </td>
   <td class="right">
<%
  if (thisChild.getGtinNumber().equals(gtinOriginal))
    out.println("<b>");  
%>   
      <%= HTMLHelpers.displayNumber(new BigDecimal(thisChild.getChildQty()), 0) %> Units&nbsp;&nbsp;&nbsp;
<%
  if (thisChild.getGtinNumber().equals(gtinOriginal))
    out.println("</b>");  
%>       
   </td> 
   <td>
   
<%
  if (thisChild.getIsBaseUnit() != null &&
      thisChild.getIsBaseUnit().equals("T"))
  {
    if (thisChild.getGtinNumber().equals(gtinOriginal))
      out.println("<b>Base Unit</b>");
    else  
      out.println("Base Unit"); 
  } 
  else
    out.println("&nbsp;"); 
%>   
   </td>  
   <td>
<%
  if (thisChild.getPublishToUCCNet() != null &&
      thisChild.getPublishToUCCNet().equals("Y"))
  {
    if (thisChild.getGtinNumber().equals(gtinOriginal))
      out.println("<b>Load Yes</b>"); 
    else 
      out.println("Load Yes"); 
  }  
  else
  {
    if (thisChild.getPublishToUCCNet() != null &&
        thisChild.getPublishToUCCNet().equals("N"))
    {
       if (thisChild.getGtinNumber().equals(gtinOriginal))
         out.println("<b>Load No</b>"); 
       else 
         out.println("Load No"); 
    }
    else
    {
       if (thisChild.getGtinNumber().equals(gtinOriginal))
         out.println("<b>Pending</b>"); 
       else 
         out.println("Pending"); 
    }
  }  
%>   
   </td>     
<%
	if (rType.equals("update"))
	{
%>   
   <td>
     <%--  <%= UpdTieToChildren.buildMoreButton("familyTree", thisChild.getGtinNumber(), thisChild.getGtinLongDescription()) %> --%>
    	<input type="button" value="More" class="moreButton" />
		<ul class="tooltip right">
			<li><a target="_blank" href="/web/CtlGTIN?requestType=detail&gtinNumber=<%= thisChild.getGtinNumber() %>">Details of <%= thisChild.getGtinNumber() %></a></li>
			<li><a target="_blank" href="/web/CtlGTIN?requestType=update&gtinNumber=<%= thisChild.getGtinNumber() %>">Update <%= thisChild.getGtinNumber() %></a></li>
			<li><a target="_blank" href="/web/CtlGTIN?requestType=updateTies&parentGTIN=<%= thisChild.getGtinNumber() %>">Maintain Children of <%= thisChild.getGtinNumber() %></a></li>
		</ul>
   </td>   
<%
    }
%>
  </tr>  
<%
      }
      }
     }// end of the Child For Loop
     }
   } // end of For Loop
%> 
 </table>
<%  
  } // No Family
%> 