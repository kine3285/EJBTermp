<%-- If shopping cart is empty and user clicks on shopping cart link, servlet forwards request to this jsp --%>
<html>
 <head><title> Jasmine's Empty Cart page</title></head>
    <BODY>
         <jsp:include page="title.jsp" />
        <font size="+2">Your Cart is empty.</font>
        <br>&nbsp;
        <br>
        <center>
            <a href="/jasmine/wsf.jsp">Back to the web storefront.</a>
        </center>
        <jsp:include page="footer.jsp" />

    </BODY>
</html>
