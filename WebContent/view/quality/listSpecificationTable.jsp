<%@ page import="com.treetop.app.quality.InqSpecification, 
    com.treetop.app.quality.DtlSpecification,
    com.treetop.app.quality.GeneralQuality,
    com.treetop.businessobjects.QaSpecification,
    java.util.Vector
" %>
<%  
 Vector getData = new Vector();
 String thisEnv = "PRD";
    InqSpecification inqSpecification = (InqSpecification) request.getAttribute("inqViewBean");
    if (inqSpecification == null) {
       inqSpecification = new InqSpecification();

       DtlSpecification dtlSpecificationTable = (DtlSpecification) request.getAttribute("dtlViewBean");
       getData = dtlSpecificationTable.getDtlBean().getRevReasonSpecification();
       thisEnv = dtlSpecificationTable.getEnvironment();
       
    } else {
    
       getData = inqSpecification.getListReport();
       thisEnv = inqSpecification.getEnvironment();
       
    }
  
    
 %> 
    
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
        
<%String displayView = (String) request.getAttribute("displayView");
 if (displayView == null)
   displayView = "";

   String columnHeadingTo = "";
   if (displayView.trim().equals(""))
       columnHeadingTo = "/web/CtlQuality?requestType=listSpec&" +
                            "&environment=" + thisEnv + 
                            inqSpecification.buildParameterResend();
   String[] sortImage = new String[7];
   String[] sortStyle = new String[7];
   String[] sortOrder = new String[7];
   sortOrder[0] = "specificationName";
   sortOrder[1] = "specificationDescription";
   sortOrder[2] = "revisionDate";
   sortOrder[3] = "status";
   sortOrder[4] = "itemNumber";
   sortOrder[5] = "specificationType";
   sortOrder[6] = "formulaNumber";
  //************
  //Set Defaults
   for (int x = 0; x < 7; x++)
   {
      sortImage[x] = "<img src='Include/images/ui-sort-default_4a3126_16x16.gif'>";
      sortStyle[x] = "";
   }
  //************
  if (displayView.trim().equals(""))
  {
   String orderBy = inqSpecification.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "SpecificationNumber";
   for (int x = 0; x < 6; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (inqSpecification.getOrderStyle().trim().equals(""))
        {
            sortImage[x] = "<img src='Include/images/ui-sort-up_4a3126_16x16.gif'>";
           //sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowUpDark.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           //sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowDownDark.gif\">";
           sortImage[x] = "<img src='Include/images/ui-sort-down_4a3126_16x16.gif'>";
     }
   }   
   }
   int imageCount = 3;
   int expandCount = 0;
%>
        
        <tr>
            <th class="left">
                <%= sortImage[5] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
                    Spec Type
                </a>
            </th>
            <th class="left">
                <%= sortImage[0] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
                    Spec Number
                </a>
            </th>
            <th class="left">
                <%= sortImage[1] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
                    Description
                </a>
            </th>
            <th class="left">
                <%= sortImage[4] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
                    Item Number
                </a>
            </th>
            <th class="left">
                <%= sortImage[4] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
                    Formula Number
                </a>
            </th>
            <th class="left">
                <%= sortImage[2] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
                    Revision<br>Date and Time
                </a>
            </th>
            <th class="left">
                <%= sortImage[3] %>
                <a href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
                    Specification<br>Status
                </a>
            </th>
            <th></th>
        </tr>
<%  for (int i=0; i<getData.size(); i++) {
        QaSpecification thisrow = (QaSpecification) getData.elementAt(i);
        String specificationUrl = "CtlQuality?requestType=dtlSpec&specNumber=" + thisrow.getSpecificationNumber() +
                       "&revisionDate=" + thisrow.getRevisionDate() +
                       "&revisionTime=" + thisrow.getRevisionTime() +
                       "&environment=" + thisEnv;
 		String formulaUrl = "/web/CtlQuality?requestType=dtlFormula&formulaNumber=" + thisrow.getFormulaNumber() +
 		//   Do not need the Revision Date and Time, if you don't send it -- program shows the Active Version
                       "&environment=" + thisEnv;
%>     
        <tr>
            <td><%=thisrow.getTypeDescription() %></td>
            <td>
                <a href="<%=specificationUrl %>">
                    <%=thisrow.getSpecificationName() %>
                </a>
            </td>
            <td><%=thisrow.getSpecificationDescription() %></td>
            <td><%=thisrow.getItemNumber() %></td>
            <td> 
            	<a href="<%=formulaUrl %>">
                    <%=thisrow.getFormulaName() %>
                </a>
            </td>
            <td><%= GeneralQuality.formatDateForScreen(thisrow.getRevisionDate()) %> <%= GeneralQuality.formatTimeForScreen(thisrow.getRevisionTime()) %></td>
            <td class="center"><%= thisrow.getStatusDescription() %></td>
            <%
              if (displayView.trim().equals(""))
              {
              // --- Build Vector for the MORE Button
              Vector sendParms = new Vector();
              sendParms.add(inqSpecification.getRequestType());
              sendParms.add(inqSpecification.getSecurityLevel());
              sendParms.add(inqSpecification.getEnvironment());
              sendParms.add(thisrow.getSpecificationNumber());
              sendParms.add(thisrow.getRevisionDate());
              sendParms.add(thisrow.getRevisionTime());
            %> 
            <td>
                <div class="no-print">
	                <input type="button" value="More" class="moreButton" />
	                <%= InqSpecification.buildMoreButton(sendParms, request, response) %>
                </div>
            </td>
            <%  } else { %>
            <td>
                <%= thisrow.getRevisionReasonText().trim() %>
            </td>
            <%  } %>
        </tr>
<%  } //end loop %>
    </table>
    </div>
    </div>