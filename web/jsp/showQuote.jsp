<%--
    This JSP displays details of the products added to the cart.
    The details includes the name of the product, quantity, base price,
    discount and total price of each line item. It also displays subtotal,
    taxes and grand total of the entire cart. Customer has provision to 
    modify the quantities, order the contents and clear the cart.
     
--%>
<HTML>
    <head><title> Jasmine's Shopping Cart page </title></head>
    <body>
        <jsp:include page="title.jsp" />
        <jsp:directive.page errorPage="error.jsp" />
        <%@ page import="examples.shop.impl.entity.Product" %>
        <%@ page import="examples.shop.impl.entity.LineItem" %>
        <%@ page import="java.util.List" %>
        <%@ page import="java.util.Iterator" %>        
        <%@ page import="java.text.NumberFormat" %>
        <form action="/jasmine/showQuote" method="get"> 
                <center><table>
                 <tr>
                     <th align=left> Name </TH>
                     <th align=left> Quantity </TH>
                     <th align=left> Individual Base Price </TH>
                     <th align=left> Discount </TH>
                     <th align=left><strong> Total Price </strong></TH>
                 </tr>
                    <%
                    // get the lineItems from the request object and displays in a table format.
                    List<LineItem> lineItems = (List<LineItem>)request.getAttribute("lineItems");
                    double subTotal=((Double)request.getAttribute("subTotal")).doubleValue();
                    double taxes=((Double)request.getAttribute("taxes")).doubleValue();
                    double taxRate=((Double)request.getAttribute("taxRate")).doubleValue();
                    double total=((Double)request.getAttribute("total")).doubleValue();
   
                    for(Iterator<LineItem> iter = lineItems.iterator(); iter.hasNext();) {
                        LineItem li = iter.next();
                        int quantity     = li.getQuantity();
                        double discount  = li.getDiscount();
                        Product product  = li.getProduct();
                        String productID = product.getProductID();
                        double basePrice = product.getBasePrice();
                    %>
                    <tr>
                        <td bgcolor="#ffffaa"><a href="/jasmine/catalog?productId=<%=product.getProductID()%>"><b><%=product.getName()%><b></td>
                        <td><input type="text" name="<%=productID%>" value="<%=quantity%>"></td>
                        <td bgcolor="#ffffaa" align="right"><%=basePrice%></td>
                        <td bgcolor="#ffffaa" align="right"><%=discount%></td>
                        <td bgcolor="#ffffaa" align="right"><%=basePrice*quantity-discount%></td>

                    </tr>
                    <% } %>
                </table></center>
                <br>
                <center><table>
                    <tr>
                        <td colspan="2" align="right" bgcolor="#ffffff"> Subtotal:</td>
                        <td bgcolor="#ffffaa" align="right"><%=subTotal%></td>
                        <td> <br></td>          
                    </tr>
                    <tr>
                        <td colspan="2" align="right" bgcolor="#ffffff">Sales Tax: </td>
                        <td bgcolor="#ffffaa" align="right"><%=taxes%></td><td align="right">(<%=taxRate%>)%</td>
                        <td><br></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right" bgcolor="ffffff"> <font color="ff0000"> <strong>Grand Total:</strong></font> </td>
                        <td bgcolor="#ffffaa" align="right"><%=total%></td>
                        <td><br></td>   
                    </tr>
                </table></center>
                <!--
                    Print out links and buttons for user feedback.
                    When the customer clicks a button to perform an 
                    action (such as submitting an order), sends request to
                    the servlet with necessary parameters to process the request.
                //-->
                <p>
                <A href="/jasmine/catalog">See the Catalog</A>
                <input type="submit" name="Update" value="Update Quantities"> &nbsp; &nbsp; &nbsp;
                <input type="submit" name="Order" value="Submit Order"> &nbsp; &nbsp; &nbsp;
                <input type="submit" name="Clear" value="Clear Cart"> &nbsp; &nbsp; &nbsp;

        <jsp:include page="footer.jsp" />
        </form>
    </body>
</html>
