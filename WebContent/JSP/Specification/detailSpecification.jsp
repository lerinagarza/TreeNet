<%@ page language="java" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.businessobjects.* %>
<%@ page import = "com.treetop.servicies.* %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "java.text.*" %>

<%
//---------------- detailSpecification.jsp ----------------------//
//     Changed name from dtlIngSpecs.jsp
//   Author :  Charlena Paschen  03/21/02
//   Changes:
//   Date       Name       Comments
// --------   ---------   ---------------------------------------//
//  3/21/05   TWalton      Changed Name, changed from JSP to Servlet Driven
//  3/09/04   TWalton      Changed comments and images for 5.0 server.
//  1/07/09   THaile       Access New 400 (rmv lot access,..)
//--------------------------------------------------------------//

//  Set Fields if No Data is Found
   String goldTitle = "Detail Specification Screen";
//**************************************************************************//
//  Retrieve Data collected in the servlet
//**************************************************************************//
   Vector            detailSpec   = new Vector();
   Specification     headerInfo   = new Specification();
   SpecificationView firstElement = new SpecificationView();
   boolean           detailFound  = false;
   try
   {
	   detailSpec = (Vector) request.getAttribute("detailSpecification");	
	   if (detailSpec != null)
	   {
	      firstElement = (SpecificationView) detailSpec.elementAt(0);
	      headerInfo   = firstElement.getSpecification();
	      if (headerInfo.getSpecificationCode() != null)
	      {
	        goldTitle = headerInfo.getSpecificationCode() + " Details";
	        detailFound = true;
	      }
	   }	   
   }
   catch(Exception e)
   {
   }
 Vector tInfoTest  = new Vector();
 int    tCountTest = 0;
 try
 {
 
    tInfoTest  = firstElement.getDocumentation();
   	tCountTest = tInfoTest.size();
 }
 catch(Exception e)
 {
 }  
 int openSection = 5 + tCountTest;   
//*****************************************************************************
   request.setAttribute("title", goldTitle);
//*****************************************************************************
  
  int imageCount  = 3;
  request.setAttribute("imageCount", new Integer(imageCount));
  int expandCount = 1;
  request.setAttribute("expandCount", new Integer(expandCount));
%>
<%@page import="com.treetop.businessobjects.Customer"%>
<html>
 <head>
   <title>Detail Specification</title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", openSection, "Y", 5) %>
 </head>
 <body>
 <jsp:include page="../include/heading.jsp"></jsp:include>
<table class="table01001" cellspacing="0" style="width:100%">
 <tr>
  <form method="link" action="#">
 
   <td style="width:3%" style="border-bottom:1px solid #006400">&nbsp;</td> 
   <td class="td041CL001" style="border-bottom:1px solid #006400">
     <input type="submit" value="Select New Specification"
        onClick="ANY_NAME=window.open('/web/JSP/Specification/selSpecification.jsp?requestType=detail', 'win1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width=300,height=300'); return false;">
   </td>
   </form>    
   
 </tr>
</table>
<table class="table01001" cellspacing="0" style="width:100%">
<%
  if (!detailFound)
  { 
%>
 <tr>
  <td class="td071CC001">There is no information for the selected Specification.<br>
                          Please choose another specification.</td>   
 </tr>  
<%  
  }
  else
  {
    if (!headerInfo.getCurrentVersion())
    {
%>
 <tr class="tr02001">
  <td class="td071CC002" style="font-size:20px" colspan="7"><b>NOT CURRENT VERSION OF SPECIFICATION</b><br>&nbsp;</td>
 </tr> 
<%
    }
%> 
 <tr>
  <td style="width:5%" rowspan="7">&nbsp;</td>  
  <td class="td071CL002">Specification ID:</td>
  <td class="td041CL002">
     <b>
<%
  if (headerInfo.getCurrentVersion())
    out.println(headerInfo.getSpecificationCode());
  else
   out.println(HTMLHelpersLinks.routerSpecification(headerInfo.getSpecificationCode().trim(), "a04002", "", ("&specificationDate="+ headerInfo.getSpecificationDate().trim())));  
%>  
  </b>&nbsp;
  </td>
  <td class="td041CL002">
   <%= headerInfo.getGeneralDescription() %>&nbsp;
  </td>      
  <td class="td071CL002">Referenced Spec:</td>
  <td class="td041CL002">
    <%= headerInfo.getReferenceTextCode() %>&nbsp;--&nbsp;<%= headerInfo.getReferenceAnalCode() %>
  </td>     
  <td style="width:5%" rowspan="7">&nbsp;</td>  
 </tr> 
 <tr>
  <td class="td071CL002">Specification Type:</td>
  <td class="td041CL002">
   <%= headerInfo.getSpecificationType() %>
  </td>
  <td class="td041CL002">
    &nbsp;
  </td>      
  <td class="td071CL002">Deleted Spec:</td>
  <td class="td041CL002">
<%
   if (headerInfo.getDeletedRecord())
      out.println("Yes");
   else
      out.println("No");
%>  
    &nbsp;
  </td>     
 </tr>     
 <tr>
  <td class="td071CL002">Customer:</td>
  <td class="td041CL002">
   <b><%= headerInfo.getCustomerNumber() %></b>&nbsp;
  </td>
  <td class="td041CL002">
<%
   try
   {
      //01/07/2009 wth - new As400
      //CustomerBillTo thisCust = new CustomerBillTo(headerInfo.getCompanyNumber(), headerInfo.getCustomerNumber()); 01/07/09 wth
      //if (thisCust.getCustomerName() != null &&
          //!thisCust.getCustomerName().equals(""))
        //out.println(thisCust.getCustomerName());
     //01/21/2009 wth - Use New Customer Service.
     if (headerInfo.getCustomerNumber() != null &&
         !headerInfo.getCustomerNumber().equals(""))
     {
        Customer thisCust = new Customer(); //01/21/2009
        thisCust.setCompany("100"); //01/21/2009
        thisCust.setNumber(headerInfo.getCustomerNumber().trim()); //01/21/2009
        thisCust = com.treetop.services.ServiceCustomer.getCustomerByNumber(thisCust); //01/21/2009
        if (thisCust.getName() != null && //01/21/2009
          !thisCust.getName().equals("")) //01/21/2009
          out.println(thisCust.getName()); //01/21/2009
      }
   }
   catch(Exception e)
   {
   }
%>    
    &nbsp;
  </td>      
  <td class="td071CL002">Revision Date:</td>
  <td class="td041CL002"><%= headerInfo.getSpecificationDate() %>&nbsp;</td>
 </tr>    
 <tr>
  <td class="td071CL002">Customer Spec:</td>
  <td class="td041CL002">
   <%= headerInfo.getCustomerSpecification() %>&nbsp;
  </td>
  <td class="td041CL002">
        &nbsp;
  </td>      
  <td class="td071CL002">Supersedes:</td>
  <td class="td041CL002"><%= headerInfo.getSupersededDate() %>&nbsp;</td>     
 </tr>  
 <tr>
  <td class="td071CL002">OE Customer:</td>
  <td class="td041CL002">
   <b>
<%
   if (headerInfo.getMarketNumber().toString().length() == 3)
     out.println(headerInfo.getMarketNumber().toString().trim());
   if (headerInfo.getMarketNumber().toString().length() == 2)
     out.println("0" + headerInfo.getMarketNumber().toString().trim());
   if (headerInfo.getMarketNumber().toString().length() == 1)
     out.println("00" + headerInfo.getMarketNumber().toString().trim());
   if (headerInfo.getMarketNumber().toString().length() == 0)
     out.println("000");

   if (headerInfo.getMarketCustomer().toString().length() == 3)
     out.println(headerInfo.getMarketCustomer().toString().trim());     
   if (headerInfo.getMarketCustomer().toString().length() == 2)
     out.println("0" + headerInfo.getMarketCustomer().toString().trim());
   if (headerInfo.getMarketCustomer().toString().length() == 1)
     out.println("00" + headerInfo.getMarketCustomer().toString().trim());
   if (headerInfo.getMarketCustomer().toString().length() == 0)
     out.println("000");
   
%>   
   </b>&nbsp;
  </td>
  <td class="td041CL002">
<%
   try
   {
      //01/07/2009 wth - new As400
      //CustomerOrderTo thisCust = new CustomerOrderTo(headerInfo.getCompanyNumber(), headerInfo.getCustomerNumber(), headerInfo.getMarketNumber(), headerInfo.getMarketCustomer());
      //if (thisCust.getOeName() != null &&
          //!thisCust.getOeName().equals(""))
        //out.println(thisCust.getOeName());
   }
   catch(Exception e)
   {
   }
%>    
    &nbsp;
  </td>      
  <td class="td071CL002">Raw Fruit Spec:</td>
  <td class="td041CL002">
<%
   if (headerInfo.getRawFruitSpecCode() != null)
   {
      out.println(HTMLHelpersLinks.routerSpecification(headerInfo.getRawFruitSpecCode(), "a04002", "", ""));
      if (headerInfo.getRawFruitSpecDate() != null)
        out.println("-" +  headerInfo.getRawFruitSpecDate());
   } 
%>  
  &nbsp;
  </td>
 </tr>    
 <tr>
  <td class="td071CL002">Item:</td>
  <td class="td041CL002">
   <%=  HTMLHelpersLinks.routerItem(headerInfo.getResourceNumber(), "a04003", "", "") %> 
  </td>
  <td class="td041CL002"><%= headerInfo.getItemDescription() %>
        &nbsp;
  </td>        <td class="td071CL002">Grade:</td>
  <td class="td041CL002">
             &nbsp;
  </td>
 </tr> 
 <tr>
  <td class="td071CL002">Plant:</td>
  <td class="td041CL002">
<%
	String sendPlant = headerInfo.getPlantNumber().toString();
    if (sendPlant.length() == 1)
      sendPlant = "0" + sendPlant;
%>  
   <%= sendPlant %>&nbsp;
  </td>
  <td class="td041CL002">
<%
        try
        {
            //01/07/2009 wth - new As400
            //Plant thisOne = new Plant(sendPlant); 
	 	   //if (thisOne.getPlantDescription() != null &&
               //!thisOne.getPlantDescription().trim().equals(""))
               //out.println(thisOne.getPlantDescription());
        }
        catch(Exception e)
        {
        }
%>
        &nbsp;
  </td>        
  <td class="td071CL002">Additive:</td>
  <td class="td041CL002">
             &nbsp;
  </td>
 </tr> 
<%
//****************************************************************************************
//  Variety Information
//****************************************************************************************
   String[] avar = headerInfo.getVarietiesAcceptable();
   int      acnt = 0;
   Vector acceptedVarieties = new Vector();
   for (int av = 0; av < avar.length; av++)
   {
      if (avar[av] != null &&
          !avar[av].trim().equals(""))
      {    
        acnt++;
        acceptedVarieties.addElement(avar[av]);
      }
   }
   String[] evar = headerInfo.getVarietiesExcluded();
   int      ecnt = 0;
   Vector   excludedVarieties = new Vector();
   for (int ev = 0; ev < evar.length; ev++)
   {
      if (evar[ev] != null &&
          !evar[ev].trim().equals(""))
      {    
        ecnt++;
        excludedVarieties.addElement(evar[ev]);
      }  
   }
   if (acnt > 0 ||
       ecnt > 0 ||
       (headerInfo.getFruitVariety() != null &&
        !headerInfo.getFruitVariety().trim().equals("")))
   {    
%> 

 <tr class="tr02001">
  <td class="td01CC001" colspan="7">
   <%= JavascriptInfo.getExpandingSection("C", "Variety Information", 12, expandCount, imageCount, 1, 0) %>
<%
   expandCount++;
   imageCount++;
%> 
    <table class="table01001" cellspacing="0" style="width:100%">
<%
   if (headerInfo.getFruitVariety() != null &&
        !headerInfo.getFruitVariety().trim().equals(""))
   {        
%>
     <tr>
      <td class="td044CL001" style="width:5%">
        &nbsp;
      </td>        
      <td class="td071CC001" colspan="2">
        <b>Main Variety:</b>
      </td>  
      <td class="td041CC001">
        <b><%= headerInfo.getFruitVariety() %></b>
      </td> 
      <td class="td041CC001" colspan="2"><b>
<%
    try
    {
      GeneralInfoDried thisV = new GeneralInfoDried("FV", headerInfo.getFruitVariety().trim());
      out.println(thisV.getDescFull());
    }
    catch(Exception e)
    {
    }
%>            
        &nbsp;</b>      
      </td>       
      <td class="td044CL001" style="width:5%">
        &nbsp;
      </td>                        
     </tr> 
<%   
   }
   	if (acnt > 0 ||
    	ecnt > 0)
    {       
%>    
     <tr class="tr04001">
      <td class="td044CL001" style="width:5%">
        &nbsp;
      </td>   
      <td class="td011CC001" colspan="3">
        <b>Included</b>
      </td>  
      <td class="td011CC001" colspan="3" style="border-left:1px solid #cccc99">
        <b>Excluded</b>
      </td> 
      <td class="td044CL001" style="width:5%">
        &nbsp;
      </td>                        
     </tr> 
<%
   for (int x = 0; x < 10; x++)
   {  
      String inclVar  = "";
      if (x < acnt)
      {
         inclVar = (String) acceptedVarieties.elementAt(x); 
         if (inclVar == null)
           inclVar = "";
      } 
      String exclVar = "";
      if (x < ecnt)
      {
         exclVar = (String) excludedVarieties.elementAt(x); 
         if (exclVar == null)
           exclVar = "";
      }  
      if (!inclVar.trim().equals("") ||
          !exclVar.trim().equals(""))
      {             
%>     
     <tr class="tr00001">
      <td></td>    
      <td class="td044CL002" style="width:5%">
        &nbsp;
      </td>         
      <td class="td044CL002" style="width:20%">
        <%= inclVar %>&nbsp;
      </td> 
      <td class="td044CL002" style="width:20%">
<%
    try
    {
      GeneralInfoDried thisV = new GeneralInfoDried("FV", inclVar.trim());
      out.println(thisV.getDescFull());
    }
    catch(Exception e)
    {
    }
%>            
        &nbsp;
      </td> 
      <td class="td044CL002" style="border-left:1px solid #cccc99;
                                    width:5%">
        &nbsp;
      </td>                
      <td class="td044CL002" style="width:20%">
        <%= exclVar %>&nbsp;
      </td>   
      <td class="td044CL002" style="width:20%">
<%
    try
    {
      GeneralInfoDried thisV = new GeneralInfoDried("FV", exclVar.trim());
      out.println(thisV.getDescFull());
    }
    catch(Exception e)
    {
    }
%>          
        &nbsp;
      </td> 
      <td></td>                            
     </tr>      
<% 
       }
     }
   }
%>     
    </table>
   </span>
  </td>
 </tr> 
<%
  }
  
  
//****************************************************************************************
// Analytical / Ident Information
//****************************************************************************************
 Vector aInfoTest  = new Vector();
 int    aCountTest = 0;
 try
 {
   	aInfoTest  = firstElement.getAnalytical();
   	aCountTest = aInfoTest.size();
 }
 catch(Exception e)
 {
 }  
 if (aCountTest > 0)
 { 
    request.setAttribute("analyticalInformation", aInfoTest);
%>  
 <tr class="tr02001">
  <td class="td01CC001" colspan="7">
   <%= JavascriptInfo.getExpandingSection("O", "Analytical / Ident Information", 12, expandCount, imageCount, 1, 0) %>
<%
   expandCount++;
   imageCount++;
   request.setAttribute("imageCount", new Integer(imageCount));
%> 
    <%@ include file="detailSpecificationAnalytical.jsp" %>   
   </span>
  </td>
 </tr>   
<%

  	  try
  	  {
     	imageCount  = ((Integer) request.getAttribute("imageCount")).intValue();     
  	  }
  	  catch(Exception e)
	  {}
  }
//****************************************************************************************
// Specification Text
//****************************************************************************************

 if (tCountTest > 0)
 { 
   	request.setAttribute("specificationText", tInfoTest);
%>  
 <tr class="tr02001">
  <td class="td01CC001" colspan="7">
   <%= JavascriptInfo.getExpandingSection("O", "Specification Text", 12, expandCount, imageCount, 1, 0) %>
<%
   expandCount++;
   imageCount++;
 
  request.setAttribute("imageCount", new Integer(imageCount));
  request.setAttribute("expandCount", new Integer(expandCount));   
%> 
    <%@ include file="detailSpecificationDocumentation.jsp" %>   
   </span>
  </td>
 </tr>   
<%
  try
  {
   expandCount = ((Integer) request.getAttribute("expandCount")).intValue();
   imageCount  = ((Integer) request.getAttribute("imageCount")).intValue();
  }
  catch(Exception e)
  {}
   }
//****************************************************************************************
// Revision Dates
//****************************************************************************************
 Vector rInfoTest  = new Vector();
 int    rCountTest = 0;
 
 try
 {
   	rInfoTest  = (Vector) request.getAttribute("reportList");
   	rCountTest = rInfoTest.size();
 }
 catch(Exception e)
 {
 }  
 if (rCountTest > 0)
 { 
%>  

 <tr class="tr02001">
  <td class="td01CC001" colspan="7">
   <%= JavascriptInfo.getExpandingSection("C", "Revision Dates", 12, expandCount, imageCount, 1, 0) %>
<%
   expandCount++;
   imageCount++;
   request.setAttribute("expandCount", new Integer(expandCount));
   request.setAttribute("imageCount", new Integer(imageCount));
%> 
     <%@ include file="listSpecificationsTable.jsp" %>
   </span>
  </td>
 </tr>   
<%
   expandCount = ((Integer) request.getAttribute("expandCount")).intValue();
   imageCount  = ((Integer) request.getAttribute("imageCount")).intValue();
   }  
  
   
//****************************************************************************************
// Additional Information
//****************************************************************************************
%>  
 <tr class="tr02001">
  <td class="td01CC001" colspan="7">
   <%= JavascriptInfo.getExpandingSection("C", "Additional Information", 12, expandCount, imageCount, 1, 0) %>
    <table class="table00001" cellspacing="0" style="width:100%">
     <tr>
      <td style="width:5%" rowspan="9">&nbsp;</td>
      <td class="td071CL002">Status Code:</td>
      <td class="td041CL002"><%= headerInfo.getStatusCode() %>&nbsp;</td>
      <td class="td071CL002">Wip Classification:</td>
      <td class="td041CL002"><%= headerInfo.getWIPClassification() %>&nbsp;</td>  
      <td style="width:5%" rowspan="9">&nbsp;</td>   
     </tr> 
     <tr>
      <td class="td071CL002">Company Number:</td>
      <td class="td041CL002"><%= headerInfo.getCompanyNumber() %>&nbsp;</td>
      <td class="td071CL002">Fruit Variety Edit:</td>
      <td class="td041CL002"><%= headerInfo.getFruitVarietyEdit() %>&nbsp;</td>  
     </tr>  
     <tr>
      <td class="td071CL002">Market Number:</td>
      <td class="td041CL002"><%= headerInfo.getMarketNumber() %>&nbsp;</td>
      <td class="td071CL002">Raw Fruit Spec Code:</td>
      <td class="td041CL002"><%= headerInfo.getRawFruitSpecCode() %>&nbsp;</td>  
     </tr>  
     <tr>
      <td class="td071CL002">Market Customer:</td>
      <td class="td041CL002"><%= headerInfo.getMarketCustomer() %>&nbsp;</td>
      <td class="td071CL002">Raw Fruit Spec Date:</td>
      <td class="td041CL002">
<%
   if (headerInfo.getRawFruitSpecDate() != null)
      out.println(headerInfo.getRawFruitSpecDate());
%>       
      &nbsp;</td>  
     </tr>                        
     <tr class="tr02001">
      <td class="td041CC002" colspan="2"><b>CREATED</b></td>
      <td class="td041CC002" colspan="2"><b>LAST UPDATED</b></td>  
     </tr>           
     <tr>
      <td class="td071CL002">Date:</td>
      <td class="td041CL002"><%= headerInfo.getCreateSpecDate() %>&nbsp;</td>
      <td class="td071CL002">&nbsp;</td>
      <td class="td041CL002"><%= headerInfo.getUpdateSpecDate() %>&nbsp;</td>  
     </tr> 
     <tr>
      <td class="td071CL002">Time:</td>
      <td class="td041CL002"><%= headerInfo.getCreateSpecTime() %>&nbsp;</td>
      <td class="td071CL002">&nbsp;</td>
      <td class="td041CL002"><%= headerInfo.getUpdateSpecTime() %>&nbsp;</td>  
     </tr> 
     <tr>
      <td class="td071CL002">User:</td>
      <td class="td041CL002"><%= headerInfo.getCreateSpecUser() %>&nbsp;
<%
   if (headerInfo.getCreateSpecUserName() != null &&
       !headerInfo.getCreateSpecUserName().trim().equals(""))
       out.println("-&nbsp;" + headerInfo.getCreateSpecUserName());
%>      
      </td>
      <td class="td071CL002">&nbsp;</td>
      <td class="td041CL002"><%= headerInfo.getUpdateSpecUser() %>&nbsp;
<%
   if (headerInfo.getUpdateSpecUserName() != null &&
       !headerInfo.getUpdateSpecUserName().trim().equals(""))
       out.println("-&nbsp;" + headerInfo.getUpdateSpecUserName());
%>          
      </td>  
     </tr>  
     <tr>
      <td class="td071CL002">Workstation:</td>
      <td class="td041CL002"><%= headerInfo.getCreateSpecWorkstation() %>&nbsp;</td>
      <td class="td071CL002">&nbsp;</td>
      <td class="td041CL002"><%= headerInfo.getUpdateSpecWorkstation() %>&nbsp;</td>  
     </tr>                            
    </table>
   </span>
  </td>
 </tr>   
<%
 }
%>        
</table>  
<jsp:include page="../include/footer.jsp"></jsp:include>
   </body>
</html>