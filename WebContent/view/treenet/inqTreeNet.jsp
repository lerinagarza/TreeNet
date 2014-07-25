<%@ page import="java.util.Vector" %>
<%@ page import="com.treetop.data.UrlFile" %>
				 
<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp"></jsp:include>
<%  String environment = (String) request.getParameter("environment");
    if (environment == null || environment.equals("")) { 
        environment="PRD";
    }
    
    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    if (roles != null && Arrays.asList(roles).contains("1")) {
        internal = true;
    }
%>
<%-- tpl:put name="headarea" --%>
	<title>TreeNet Home</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
		<%
		// grab applications data
		Vector urls = (Vector) request.getAttribute("listTreeNetMenu");
		if (urls == null) {
			urls = new Vector();
		}	
		%>
		
		<%
		// check if the page is a search result
		String  defaultSearchVal = "Search for an Application";
		boolean isSearchResult = false;
		String paramType   = request.getParameter("type");
		String searchQuery = request.getParameter("typevalue");
		if (paramType != null && searchQuery != null) {
			// true if the "type" parameter is "search" and the "typevalue" parameter is not empty
			// or "Search..."
			boolean validQuery = !searchQuery.trim().equals("") && 
								 !searchQuery.trim().equals("Search...") &&
								 !searchQuery.trim().equals(defaultSearchVal);
			isSearchResult = paramType.equals("search") && validQuery;	
		}
		%>
		<% if (isSearchResult) { %>
		<div id="menu-search" class="">
			<form id="menu-search-form" action="${pageContext.request.contextPath}/TreeNetInq" method="post">
				<input type="hidden" name="type" value="search" />
				<span id="search-wrapper" class="clearfix">
					<input id="menu-searchbar" type="text" name="typevalue" value="<%= searchQuery %>" />
					<button id="menu-searchbutton" type="submit" 
						onclick="if($('#menu-searchbar').val() == '<%= defaultSearchVal %>'){ 
							$('#menu-searchbar').val(''); 
						}">
						Search
					</button>
				</span>
			</form>
			
			<% if (urls.isEmpty()) { %>
			<span id="search-results" class="ui-state-highlight ui-corner-all">
				<em>
					No results for:
				</em>
				<strong><%=" \"" + searchQuery + "\"" %></strong>
			</span>
			<% } %>
	
		</div>
		<% } %>	
		
		<%	
		// draw applications lists if urls has them, otherwise draw nothing
		UrlFile lastUrl = new UrlFile(); 
		if (!urls.isEmpty()) {
			lastUrl = (UrlFile) urls.elementAt(0) ;

			for (int i = 1; !urls.isEmpty() && i < urls.size(); i++) { 
				UrlFile thisUrl = (UrlFile) urls.elementAt(i);
				boolean lastUrlIsLink = false;
				if (!lastUrl.getType().trim().equals("major") && !lastUrl.getType().trim().equals("minor")) {
					lastUrlIsLink = true; // true if neither major nor minor
				}
		%>
		
		<% if(lastUrl.getType().trim().equals("major")) { %>
			<div class="major">
				<h1><%=lastUrl.getTitle().trim() %></h1>
				<div class="major-content">
		<% } // end if major %>
				
					<% if(lastUrl.getType().trim().equals("minor")) { %>
						<h3 class="minor expand ui-widget-header"><%=lastUrl.getTitle().trim() %></h3>
						<div class="collapse ui-widget-content no-bg">
							<ul class="link-list">
					<% } %>
						
						<% if(lastUrlIsLink) { 
								// NOTE: this code is duplicated at the bottom of the page to 
								// ensure the last link is displayed.
								boolean canIUseIt = lastUrl.getCanIUseIt().trim().equals("Y");%>
								<li class="row-fluid<% if(canIUseIt) { %> unlocked<% } %>">
								<% 
								if(canIUseIt) {
								%>						
									<a href="<%=lastUrl.getUrlAddress().trim() %>" target="_blank" class="clearfix">
										<div class="span4 link">
	                                       <%=lastUrl.getTitle().trim() %>
	                                    </div>
										<div class="span8 comment">
										   <%=lastUrl.getUrlDescription().trim() %>
										</div>
									</a>
								<% } else { %>
								    <div class="span4">
								        <span class="ui-icon ui-icon-locked locked"></span>
								        <%=lastUrl.getTitle().trim() %>
								    </div>
								    <div class="span8 comment">
								        <%=lastUrl.getUrlDescription().trim() %>
								    </div>
								<% } %>	
								</li>
							<% } %>
					<%
					// close tags for end of link > minor  
					if(lastUrlIsLink && thisUrl.getType().trim().equals("minor")) { 
					%>
							</ul>
						</div>
					<% } %>
			<%
			// close tags for end of minor > major 
			if(lastUrlIsLink && thisUrl.getType().trim().equals("major")) { 
			%>
	
							</ul>
						</div>
				</div>
			<%="</div>" %>
		<%
			} 
			lastUrl = thisUrl; 
		} // end major loop  
		%>	
	 							<%
	 							// render last link
	 							boolean canIUseIt = lastUrl.getCanIUseIt().trim().equals("Y");
	 							%>
								<li class="row-fluid <% if(canIUseIt) { %>unlocked<% } %>">
								<% 
								if(canIUseIt) {
								%>		
							    <a href="<%=lastUrl.getUrlAddress().trim() %>" target="_blank" class="clearfix">
                                   <div class="span4 link">
                                       <%=lastUrl.getTitle().trim() %>
                                    </div>
                                    <div class="span8 comment">
                                       <%=lastUrl.getUrlDescription().trim() %>
                                    </div>
                                   </a>
								<% } else { %>
							    <div class="span4">
                                       <span class="ui-icon ui-icon-locked locked"></span>
                                       <%=lastUrl.getTitle().trim() %>
                                   </div>
                                   <div class="span8 comment">
                                       <%=lastUrl.getUrlDescription().trim() %>
                                   </div>
								<% } %>	
								</li>
		<% out.print("</ul></div></div></div>"); %>
	<% } // end if %>
							
		<script type="text/javascript">
		
			// search bar text effect
		    var defaultSearchVal = "<%=defaultSearchVal %>";
			$("#menu-searchbar").focus(function(){
				if ($(this).val() == defaultSearchVal) {
					$(this).val("").removeClass("blur");
				}
			}).blur(function() {
				if ($(this).val() == "") {
					$(this).val(defaultSearchVal).addClass("blur");
				}	
			});
			
			// open minors if search result
			if(<%=isSearchResult %>) { 
				$("h3.minor").each(function() {
					$(this).removeClass("expand").addClass("expandOpen");
				});
			}
			
			// link hover behavior
			$("li.unlocked").hover(function(){
				$(this).addClass("colored");
			}, function() {
				$(this).removeClass("colored");
			});
			
		</script>
		
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>