<%
   // Obtain authority object for this session.
   // Obtain server
   // Obtain URI
   // Obtain current user profile
   // Obtain AS/400 current system date

   String auth = request.getHeader("Authorization");
   String theurl = request.getHeader("Host");
   theurl = theurl + request.getRequestURI();
   String user = com.treetop.Security.getProfile(auth);
   String dateArray[] = com.treetop.SystemDate.getSystemDate();
   String serverName = request.getServerName();

%>