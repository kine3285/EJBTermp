<%-- 
     This JSP page displays Jasmine's main screen.
     Customers can navigate to the catalog as well as 
     to the shopping cart page.
--%>
 
<html>
    <head>
        <title>Jasmine's Main page</title>
    </head>
    <body>
        <jsp:include page="title.jsp" />
        <jsp:directive.page errorPage="error.jsp" />

        <h3><A href="/jasmine/catalog"><b>Catalog</b></A></h3>
        <h4><p> Choose from our excellent selection of computer parts.</p><br></h4>
        <h3><A href="/jasmine/showQuote"><b>Shopping Cart</b></A></h3>
        <h4><p> Look at your shopping cart to see the equipment you've chosen.</p></h4>

        <jsp:include page="footer.jsp" />
    </body>
</html>
