<%--This JSP displays order confirmation. When user clicks on order button,
    Order will be created in the database and order confirmation number will be 
    displayed to the customer. 
 --%>
<html>
   <head><title> Jasmine's Order Confirmation page </title></head>
   <body>
        <jsp:include page="title.jsp" />
        <jsp:directive.page errorPage="error.jsp" />
	<%-- Retrieves Order Id from request object and displays.--%>
        <h3>Thank you for shopping with us.
        <p>Your order number is <%=request.getAttribute("orderID")%>
        <p>Please shop with us again soon!
        </h3>
        <p><i><a href="/jasmine/wsf.jsp">Click here to return to the main page.</a></i>
        <jsp:include page="footer.jsp" />
   </body>
</html>

