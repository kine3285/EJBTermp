<%--
 This JSP displays a login screen.  When the user fills out the login 
 screen, it will submit it to the Login Servlet, which will verify the 
 user's credentials by calling EJB components.
 
 If the verification is unsuccessful, the login servlet will return
 the user to this page to re-enter his credentials.
 
 If the verification is successful, Jasmine's main page will be displayed.
--%>

<html>
<head>
 <title>Jasmine's Login page </title>
</head>

<body>

<%-- Include the title, which is "Jasmine's Computer Parts"--%>
<jsp:include page="title.jsp" />

<%-- Indicate the error page to use if an error occurs --%>
<jsp:directive.page errorPage="error.jsp" />

<%-- Display the login form --%>
<h4>Please enter login information</h4>
<p>
<form action="/jasmine/login" method="get">
 <table>
  <tr>
   <td><b>Name:</b></td>
   <td>
    <input type="text" name="Login" size="19"/>
   </td>
  </tr>
  <tr>
   <td><b>Password:</b></td>
   <td>
    <input type="text" name="Password" size="19"/>
   </td>
  </tr>
  <tr>
   <td></td>
   <td>
    <input type="submit" value="Submit Information"/> 
    <input type="submit" value="Register"/>
   </td>
  </tr>
 </table>
</form>

<%
 // get whether the person logged in successfully
 Boolean failed = (Boolean) request.getAttribute("loginFailed");
 if (failed != null) {
  if (failed.booleanValue() == true) {
%>
  <p>
  <strong>Could not log in!  Please try again.</strong>
  <p>
<%
  }
 }
%>

<%-- Include the page footer --%>
<jsp:include page="footer.jsp" />

</body>
</html>
