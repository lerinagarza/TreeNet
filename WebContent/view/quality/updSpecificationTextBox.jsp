<%@ page import = "
	com.treetop.app.quality.UpdSpecification,
	com.treetop.businessobjects.KeyValue,
    java.util.Vector
   " %>
<%
//----------------------- updTextBox.jsp ------------------------------//
//  Text Box Code is done the same way across the application
//  Author :  Teri Walton  09/10/13
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
   String txtReadOnly 		= "Y";
   Vector txtInformation 	= new Vector();
   String txtSpecNumber 	= "";
   String txtRevisionDate 	= "";
   String txtRevisionTime 	= "";
   String txtCommentName 	= "";
   String txtEnvironment	= "";
   boolean sequenced        = true;
   try{
   		UpdSpecification txtUpdSpec = (UpdSpecification) request.getAttribute("updViewBean");
   		txtReadOnly = (String) request.getAttribute("readOnlySpec");
   		txtCommentName = (String) request.getAttribute("textCommentName");
   		txtSpecNumber = txtUpdSpec.getSpecNumber();
   		txtRevisionDate = txtUpdSpec.getRevisionDate();
   		txtRevisionTime = txtUpdSpec.getRevisionTime();
   		txtEnvironment = txtUpdSpec.getEnvironment();
   		if (txtCommentName.trim().equals("SpecRevisionComment1"))
   		   txtInformation = txtUpdSpec.getListAnalyticalTestComments();
   		if (txtCommentName.trim().equals("SpecRevisionComment2"))
   		   txtInformation = txtUpdSpec.getListProcessParameterComments();   
   		if (txtCommentName.trim().equals("SpecRevisionComment3"))
   		   txtInformation = txtUpdSpec.getListMicroTestComments();
   		if (txtCommentName.trim().equals("SpecRevisionComment4"))
   		   txtInformation = txtUpdSpec.getListAdditivesAndPreservativeComments();  
   		if (txtCommentName.trim().equals("SpecRevisionComment5")) {
   		   txtInformation = txtUpdSpec.getListContainerPrintByLine();
   		   sequenced = true;
   		}   
   		if (txtCommentName.trim().equals("SpecRevisionComment6"))
   		   txtInformation = txtUpdSpec.getListContainerPrintAdditional();  
   		if (txtCommentName.trim().equals("SpecRevisionComment7"))
   		   txtInformation = txtUpdSpec.getListCasePrintByLine();   
   		if (txtCommentName.trim().equals("SpecRevisionComment8"))
   		   txtInformation = txtUpdSpec.getListCasePrintAdditional();  
   		if (txtCommentName.trim().equals("SpecRevisionComment9"))
   		   txtInformation = txtUpdSpec.getListPalletPrintByLine();   
   		if (txtCommentName.trim().equals("SpecRevisionComment10"))
   		   txtInformation = txtUpdSpec.getListPalletPrintAdditional();    
   		if (txtCommentName.trim().equals("SpecRevisionComment11"))
   		   txtInformation = txtUpdSpec.getListLabelPrintByLine();   
   		if (txtCommentName.trim().equals("SpecRevisionComment12"))
   		   txtInformation = txtUpdSpec.getListLabelPrintAdditional(); 
   		if (txtCommentName.trim().equals("SpecRevisionComment13"))
   		   txtInformation = txtUpdSpec.getListShelfLifeRequirements();    
   		if (txtCommentName.trim().equals("SpecRevisionComment14"))
   		   txtInformation = txtUpdSpec.getListStorageRequirements();                       
   		if (txtCommentName.trim().equals("SpecRevisionComment15"))
   		   txtInformation = txtUpdSpec.getListFinishedPalletAdditional();
   		if (txtCommentName.trim().equals("SpecRevisionComment16"))
   		   txtInformation = txtUpdSpec.getListFruitVarietiesAdditional();  
   		if (txtCommentName.trim().equals("SpecRevisionComment17"))
   		   txtInformation = txtUpdSpec.getListShippingRequirements();
   		if (txtCommentName.trim().equals("SpecRevisionComment18"))
   		   txtInformation = txtUpdSpec.getListCOARequirements();     
   		if (txtCommentName.trim().equals("SpecRevisionComment19"))
   		   txtInformation = txtUpdSpec.getListCartonPrintByLine();
   		if (txtCommentName.trim().equals("SpecRevisionComment20"))
   		   txtInformation = txtUpdSpec.getListCartonPrintAdditional();    
   		if (txtCommentName.trim().equals("SpecRevisionComment21"))
   		   txtInformation = txtUpdSpec.getListProductDescription();
   		if (txtCommentName.trim().equals("SpecRevisionComment22"))
   		   txtInformation = txtUpdSpec.getListIngredientStatement();
   		if (txtCommentName.trim().equals("SpecRevisionComment23"))
   		   txtInformation = txtUpdSpec.getListIntendedUse();   
   		if (txtCommentName.trim().equals("SpecRevisionComment24"))
   		   txtInformation = txtUpdSpec.getListForeignMatter(); 
   		if (txtCommentName.trim().equals("SpecRevisionComment25"))
   		   txtInformation = txtUpdSpec.getListCodingRequirementsAdditional();       
   }catch(Exception e){
   }
%> 
<%
  if (txtReadOnly.trim().equals("Y"))
  {
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "comment");
     request.setAttribute("showSequence", "Y");
     request.setAttribute("listKeyValues", txtInformation);    
%>   
    <div class="collapse ui-widget-content no-bg">
  		<jsp:include page="/view/utilities/updKeyValuesV3.jsp"></jsp:include>
  	</div>   
<%  }else{ %>
    <div class="collapse ui-widget-content ui-corner-all">
         <%  KeyValue keys = new KeyValue();
             keys.setEnvironment(txtEnvironment);
             keys.setEntryType(txtCommentName);
             keys.setKey1(txtSpecNumber);
             keys.setKey2(txtRevisionDate);
             keys.setKey3(txtRevisionTime);
             keys.setKey4("");
             keys.setKey5("");
             keys.setSequenced(sequenced);
             keys.setVisibleOnLoad(true);
             keys.setViewOnly(false);
             keys.setManagedByTT(true);
             keys.setHeaderText(null);
             request.setAttribute("keys",keys); %>
        <jsp:include page="../utilities/commentSection.jsp"></jsp:include>
    </div>
<%
   }
%> 

