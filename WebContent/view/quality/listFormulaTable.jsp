<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="
    com.treetop.app.quality.InqFormula,
    com.treetop.app.quality.DtlFormula,
    com.treetop.app.quality.GeneralQuality,
    com.treetop.businessobjects.QaFormula,
    com.treetop.utilities.html.HTMLHelpersLinks,
    java.util.Vector
" %>
<% 
//---------------  listFormulaTable.jsp  ------------------------------------------//
//  Directly included in the listFormula.jsp
// 
//    Author :  Teri Walton  6/29/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqFormula inqFormulaTable = new InqFormula();
 Vector getData = new Vector();
 String thisEnv = "PRD";
 try {
	inqFormulaTable = (InqFormula) request.getAttribute("inqViewBean");
//	BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
    if (inqFormulaTable == null) {
       DtlFormula dtlFormulaTable = (DtlFormula) request.getAttribute("dtlViewBean");
       getData = dtlFormulaTable.getDtlBean().getRevReasonFormula();
       thisEnv = dtlFormulaTable.getEnvironment();
    } else {
	   getData = inqFormulaTable.getListReport();
	   thisEnv = inqFormulaTable.getEnvironment();
	}
 } catch(Exception e) { }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayView = (String) request.getAttribute("displayView");
 if (displayView == null) {
   displayView = "";
   }

   String columnHeadingTo = "";
   if (displayView.trim().equals(""))
       columnHeadingTo = "/web/CtlQuality?requestType=listFormula&" +
                            "&environment=" + thisEnv + 
                            inqFormulaTable.buildParameterResend();
   String[] sortImage = new String[6];
   String[] sortStyle = new String[6];
   String[] sortOrder = new String[6];
   sortOrder[0] = "formulaNumber";
   sortOrder[1] = "formulaDescription";
   sortOrder[2] = "revisionDate";
   sortOrder[3] = "status";
   sortOrder[4] = "itemNumber";
   sortOrder[5] = "formulaType";
  //************
  //Set Defaults
   for (int x = 0; x < 6; x++)
   {
      sortImage[x] = "<img src='Include/images/ui-sort-default_4a3126_16x16.gif'>";
      sortStyle[x] = "";
   }
  //************
  if (displayView.trim().equals(""))
  {
   String orderBy = inqFormulaTable.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "formulaNumber";
   for (int x = 0; x < 6; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (inqFormulaTable.getOrderStyle().trim().equals(""))
        {
           sortImage[x] = "<img src='Include/images/ui-sort-up_4a3126_16x16.gif'>";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src='Include/images/ui-sort-down_4a3126_16x16.gif'>";
     }
   }   
   }
   int imageCount = 3;
   int expandCount = 0;
%>

<%  if (getData.isEmpty()) { %>
    <div class="ui-comment">
        
    </div>
<%  } else { %>


<div class="row-fluid">
<div class="span12">
<table class="styled full-width row-highlight">
    <colgroup>
        <col>
        <col>
        <col>
        <col>
        <col>
        <col>
        <col>
    </colgroup>
	<tr>
		<th>
		<%  if (displayView.trim().equals("")) { %>         
			<%= sortImage[5] %>
			<a title="Formula Type" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
			    Type
			</a>
		<%   } else { %>
			Type
		<%   } %>        
		</th>
		<th class="center">
		<%  if (displayView.trim().equals("")) { %>       
			<%= sortImage[0] %>
			<a title="Formula Number" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
			    Formula Number
			</a> 
		<%   } else { %>
            Formula Number
        <%   } %>        
		</th>
		<th>
		<%  if (displayView.trim().equals("")) { %>          
			<%= sortImage[1] %>
			<a title="Description of the Formula" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
			    Description
			</a>
		<%   } else { %>
            Description
        <%   } %>      
		</th>      
		<th>
		<%  if (displayView.trim().equals("")) { %>            
			<%= sortImage[4] %>
			<a title="Item Assigned to the Formula" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
			    Item Number
			</a>   
		<%   } else { %>
            Item Number
        <%   } %>      
		</th>          
		<th>
		<%  if (displayView.trim().equals("")) { %>            
			<%= sortImage[2] %>
			<a title="Sort by Revision Date and Time" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
			Code and<br>Revision<br>Date and Time
			</a>
        <%   } else { %>
            Code and<br>Revision<br>Date and Time
        <%   } %>      
		</th>      
		<th class="center">
		<% if (displayView.trim().equals("")) {%>     
			<%= sortImage[3] %>
			<a title="Status of the Formula" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
			Formula<br>Status
			</a>
        <%   } else { %>
            Formula<br>Status
        <%   } %>  
		</th>   
		
		<%  if (displayView.trim().equals("")) { %>       
		<th></th> 
		<%  } else { %>    
			<%  if (displayView.trim().equals("detail")) { %> 
			<th>Revision Reason</th>   
			<%  } %> 
		<%  } %> 
	
	</tr> 
 <%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------
  // DATA SECTION
  int dataCount = 0;
  if (getData.size() > 0) { // IF there are LOAD Records
    for (int x = 0; x < getData.size(); x++) {
      dataCount++;
      QaFormula thisrow = (QaFormula) getData.elementAt(x);
      String formulaURL = "/web/CtlQuality?requestType=dtlFormula&formulaNumber=" + thisrow.getFormulaNumber() +
                       "&revisionDate=" + thisrow.getRevisionDate() +
                       "&revisionTime=" + thisrow.getRevisionTime() +
                       "&environment=" + thisEnv;
       String formulaMouseover = "Click here to see the details of this Formula";
%>     
	<tr>
		<td><%= thisrow.getTypeDescription() %></td>
		<td><%=HTMLHelpersLinks.basicLink(thisrow.getFormulaName(), formulaURL, formulaMouseover,"" , "") %></td>
		<td><%= thisrow.getFormulaDescription() %></td>
		<td><%= thisrow.getLineTankItem().trim() %> <%= thisrow.getLineTankItemDescription() %></td>
		<td title="Code assigned to this Formula - Last Date and Time this Formula was Revised">
			<%= thisrow.getFormulaNumber() %><br>
			<%= GeneralQuality.formatDateForScreen(thisrow.getRevisionDate()) %> <%= GeneralQuality.formatTimeForScreen(thisrow.getRevisionTime()) %>
		</td>   
		<td><%= thisrow.getStatusDescription() %></td> 
		    
		<%  if (displayView.trim().equals("")) {
		  // --- Build Vector for the MORE Button
		  Vector sendParms = new Vector();
		  sendParms.add(inqFormulaTable.getRequestType());
		  sendParms.add(inqFormulaTable.getSecurityLevel());
		  sendParms.add(inqFormulaTable.getEnvironment());
		  sendParms.add(thisrow.getFormulaNumber());
		  sendParms.add(thisrow.getRevisionDate());
		  sendParms.add(thisrow.getRevisionTime());
		%>    
		<td>
		  <div class="no-print">
			  <input type="button" value="More" class="moreButton" />
			  <%= InqFormula.buildMoreButton(sendParms) %>
		  </div>
		</td>
		<%  } else { %>  
		<td>
		  <%= thisrow.getRevisionReasonText().trim() %>
		</td>
		<%  } %>
	</tr>   
<% } // end of the for loop %> 
<% } // end of the if no load records chosen %>
</table>
</div>
</div>  
<%  } //end if getData.isEmpty() %>
