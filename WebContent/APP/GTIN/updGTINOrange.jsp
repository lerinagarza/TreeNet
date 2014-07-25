<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%

//---------------  updGTINOrange.jsp  ------------------------------------------//
//   To Be included in the dtlGTIN Page
//
//   Author :  Teri Walton  9/8/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
  String errorPageTable = "/GTIN/updGTINOrange.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdGTIN updGTINOrange = new UpdGTIN();
 try
 {
	updGTINOrange = (UpdGTIN) request.getAttribute("updViewBean");
 }
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }  
 String[] canUpdateOrange = (String[]) request.getAttribute("canUpdate");
 String roOrange = "Y";  
 if ((canUpdateOrange[0].equals("Y") ||
     canUpdateOrange[2].equals("Y")) && updGTINOrange.getPublishToUCCNet().equals(""))
     roOrange = "N";  
String mouseoverHelpOrange = "Click here to see help documents.";     
%>
<html>
  <head>
    <%= JavascriptInfo.getNumericCheck() %>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Height" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Height:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("height", updGTINOrange.getHeight(), "Height", 15, 15, "", roOrange) %>
     
     <%= updGTINOrange.getHeightError() %>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Linear_Units" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Linear Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildDropDown("linearUOM", updGTINOrange.getLinearUOM().trim(), roOrange) %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>    
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Width" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Width:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("width", updGTINOrange.getWidth(), "Width", 15, 15, "", roOrange) %>
     
     <%= updGTINOrange.getWidthError() %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Volume" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Volume:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("volume", updGTINOrange.getVolume(), "Volume", 9, 9, "", roOrange) %>
     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Depth" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Depth:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("depth", updGTINOrange.getDepth(), "Depth", 15, 15, "", roOrange) %>
     
     <%= updGTINOrange.getDepthError() %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Volume_Units" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Volume Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildDropDown("volumeUOM", updGTINOrange.getVolumeUOM().trim(), roOrange) %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>    
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Net_Content" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Net Content:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("netContent", updGTINOrange.getNetContent(), "Net Content", 10, 10, "", roOrange) %>
     <%= updGTINOrange.getNetContentError() %>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Net_Content_Units" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Net Content Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildDropDown("netContentUOM", updGTINOrange.getNetContentUOM().trim(), roOrange) %>     
   </td>  
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Net_Weight" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Net Weight:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("netWeight", updGTINOrange.getNetWeight(), "Net Weight", 15, 15, "", roOrange) %>
     <%= updGTINOrange.getNetWeightError() %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Gross_Weight" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Gross Weight:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("grossWeight", updGTINOrange.getGrossWeight(), "Gross Weight", 15, 15, "", roOrange) %>
     <%= updGTINOrange.getGrossWeightError() %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	   <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Weight_Units" 
	      target="_blank" title="<%= mouseoverHelpOrange %>">Weight Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildDropDown("weightUOM", updGTINOrange.getWeightUOM().trim(), roOrange) %>     
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
 </table>
 </body>
</html>