<%-- This JSP Retrieves products vector from HttpSession and displays in table format.
     The product details includes name the product and its base price. User can view the 
     product details by clicking on name of the product. User can also add a product to 
     the shopping cart by clicking on add to cart link. When user clicks on add to cart 
     link, item is added to the cart and displays same at the bottom of the screen.

--%>
<%@ page import="examples.shop.impl.entity.Product" %>
<html>
    <head><title> Jasmine's Catalog page </title></head>
    <body>
        <jsp:include page="title.jsp" />
        <h3><A HREF="/jasmine/showQuote">View Current Shopping Cart</A></h3>
        <h3>Please choose from our selections</h3>
        <center><table>        
        <%
	//Retrieves catalog vector from HTTPSession and displays product item details in a table format
        session = request.getSession(false);
        java.util.Vector products=(java.util.Vector)session.getAttribute("products");
        int size=products.size();
        Product item=null;
        for(int i=0; i<size;i++){
            item=(Product)products.elementAt(i);
            %>
            <tr>
                <td bgcolor="#ffffaa"><a href="/jasmine/catalog?productId=<%=item.getProductID()%>"><b><%=item.getName()%><b></td>
                <td align="right" bgcolor="#ffffaa"><%=item.getBasePrice()%></td>        
                <td bgcolor="#ffffaa"> <A HREF="/jasmine/catalog?Buy=<%=item.getProductID()%>">  Add to Cart</A></td>
            </tr>
        <%}%>
        </table></center>
        <P>	   
            <%
	    // Displays the name of the product item added to the cart, at the bottom of the page.
            String productName = (String) request.getAttribute("ProductPurchased");
            if (productName != null) {
            %>
                <i> <%=productName%></i> has been added to your shopping cart.
         <% } %>

        <jsp:include page="footer.jsp" />

    </body>
</html>
