<%--This JSP displays details of the selected product.
    Customer can add this item to the shopping cart by clicking on add to cart link.
    Customer has provision to navigate to the catalog as well as to the shopping cart pages.
 --%>
<%@ page import="examples.shop.impl.entity.Product" %>

<html>
    <head><title> Jasmine's Product Info page</title></head>
    <body>
        <jsp:include page="title.jsp" />   
        <jsp:directive.page errorPage="error.jsp" />
	    
        <%    
	 //Retrieves the productItem from request object and displays.        
         Product item=(Product)request.getAttribute("productItem");
        %>
        
      <b><%=item.getName()%><b>
      <h4> Description:</h4>
       <%=item.getDescription()%> 
      <h4>Base price (before discounts):<%=item.getBasePrice()%></h4> 

      <center>
      <A href="/jasmine/catalog?Buy=<%=item.getProductID()%>">Add this item to Cart</A><br>
      <A href="/jasmine/catalog">See the Catalog</A><br>
      <A href="/jasmine/showQuote">View Current Shopping Cart</A>

        <jsp:include page="footer.jsp" />

     </center>
   </body>
</html>
