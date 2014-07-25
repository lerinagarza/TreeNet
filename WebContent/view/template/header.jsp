<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<%  String linkToHomePage = request.getContextPath() + "/TreeNetInq"; 

    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    
    //if the user has role "1" (Tree Top Employee)
    //set internal to true
    if (roles != null) {
        if (Arrays.asList(roles).contains("1")) {
            internal = true;
        }
    }
    
    //if internal
    //  show search bar, TreeNet home link, and The Trunk link
    
    //if external
    //  hide search bar, do not show TreeNet home link, and The Trunk link
    //  and remove image links for Tree Top logo and and TreeNet image logo
    

%>

<%-- need to include this div#header wrapper for the IE print style to function --%>
<div id="header">    
<header>
    <%  String environment = (String) request.getParameter("environment");
    if (environment != null && !environment.equals("") && !environment.equals("PRD")) { %>
    <div id="environment">
    *<%=environment %>*&nbsp;Environment
    </div>
    <%  }  %>
    <section class="clearfix">
        <div id="logos">
        
            <%  if (!internal) { linkToHomePage = "#"; }%>
            
            <a href="<%=linkToHomePage %>" id="treeTopLogo">
                <img src="${pageContext.request.contextPath}/Include/images/TT_logo2C-2013.png" alt="Tree Top, Inc." />
            </a>
            <!-- 
            <a href="<%=linkToHomePage %>" id="treeNetLogo">
                <img src="${pageContext.request.contextPath}/Include/images/treenet.png" alt="TreeNet" />
            </a>
             -->
        </div>
        
        <%  if (internal) { %>
        <div id="search">
            <form action="${pageContext.request.contextPath}/TreeNetInq"
            method="post" onsubmit="$(this).find('input[type=submit]').val(' ... ');">
            <input type="hidden" name="type" value="search"> 
            <input
            class="searchfield" type="text" name="typevalue" value="Search..."
            onfocus="if (this.value == 'Search...') {this.value = '';}"
            onblur="if (this.value == '') {this.value = 'Search...';}">
            <input class="noSubmit" type="submit" value="Go">
            </form>
        </div>
        <%  } %>
    
        <div id="treeNetTypeLogo">
            <b>T</b>ree<b>N</b>et
        </div>
    
    </section>

    <nav>
        <ul>
            <%   if (internal) { %>
            <%//need to put the links in a scriptlet
            //for an unknown reason, the links were being appended
            //with the context root,  VERY STRANGE! %>
            <li><a href="<%=linkToHomePage %>">Home</a></li>
            <%   } %>
            
            <li><a href="<%= "http://www.treetop.org" %>">Grower Site</a></li>
            
            <%   if (internal) { %>
            <li><a href="<%= "http://thetrunk.treetop.com" %>">The Trunk</a></li>
            <%   } %>
            
            <li><a href="<%= "http://www.treetop.com" %>">Tree Top Site</a></li>
            <li><a href="<%= "http://www.nwnaturals.com" %>">Northwest Naturals</a></li>
        </ul>
    </nav>  <!-- /#navigation -->
</header>  <!-- /#header-wrapper -->
</div>

    <noscript>
        <div class="center ui-state-highlight" style="width:60%; min-width:400px; margin:2em auto; padding:1em;">
        Hmmm...  It does not look like your browser has JavaScript enabled.  Too bad.<br>
        If you want be able to see and do all the cool stuff we made for you, enable it.<br>
         <a href="http://www.enable-javascript.com" target="_blank">Click here to find out how!</a>
        </div>
    </noscript>
    
    <div class="container-fluid">