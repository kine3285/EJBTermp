package examples.shop.web.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import examples.shop.impl.entity.LineItem;
import examples.shop.impl.entity.Product;
import examples.shop.logic.Cart;
import examples.shop.logic.Catalog;

/**
 * This servlet displays a catalog of products to
 * the end-user.
 *
 * By the time this servlet is called, the user has
 * logged in (via the Login servlet), and has a shopping
 * cart started (i.e. Cart bean).  Since this servlet is
 * pooled and re-used for different user requests, the
 * servlet code does not store any information specific to
 * any user.  Rather, we store a reference to the user's
 * Cart in the user's HttpSession object, which is
 * globally accessible to all servlets.
 */
public class CatalogServlet extends HttpServlet {

    public Catalog catalog;

    /**
     * The servlet engine calls this method once to
     * initialize a servlet instance.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Context ctx = new InitialContext();
            catalog = (Catalog)ctx.lookup(Catalog.class.getName());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * The servlet engine calls this method when the user's
     * desktop browser sends an HTTP request.
     */
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = null;
        try {
            /*
             * Get the user's HttpSession, and from that get
             * the user's current cart.
             */
            HttpSession session = request.getSession(false);
            if (session == null) {
                /*
                 * Redirect user to login page if he
                 * doesn't have a session.
                 */
                response.sendRedirect(response
                        .encodeRedirectURL("/jasmine/login"));
                return;
            }

            Object obj = session.getAttribute("cart");
            if (obj == null) {
                /*
                 * Redirect user to login page if he
                 * doesn't have a session.
                 */
                response.sendRedirect(response
                        .encodeRedirectURL("/jasmine/login"));
                return;
            }
            Cart cart = (Cart) obj;
            String productIDToAdd = request.getParameter("Buy");
            products = (Vector) session.getAttribute("products");
            String productId = (String) request.getParameter("productId");

            /*
             * If user wants to purchase something (via
             * the URL parameter 'Buy'), add the desired
             * product item to the cart.
             */

            if (productIDToAdd != null) {
                /*
                 * Creates LineItem, and add to the cart.
                 */
                try {
                    Product item = getProductItem(products, productIDToAdd);
                    cart.add(new LineItem(item, 1, 0));
                    // Set a flag so that JSP knows which product we purchased
                    request.setAttribute("ProductPurchased", item.getName());
                    // Forwards the request to the catalog JSP
                    this.getServletContext().getRequestDispatcher(
                            "/catalog.jsp").forward(request, response);
                    return;
                } catch (Exception e) {
                    throw new ServletException(e.toString());
                }
            }
            /*
             * If user wants to view the product details (via
             * the URL parameter 'productId')
             */
            else if (productId != null) {
                //Retrieves the product item from the prducts vector and pass it in request object.
                request.setAttribute("productItem", getProductItem(products,
                        productId));
                // Forwards the request to the productInfo JSP
                this.getServletContext().getRequestDispatcher(
                        "/productInfo.jsp").forward(request, response);
                return;
            }
            /*
             * If products vector = null,Retrieves productItems vector
             * from Catalog stateless session bean and put it in the
             * HttpSession. we may need to put this vector in
             * application level instead of session.
             */
            else {
                if (products == null) {
                    products = catalog.getProductList();
                    session.setAttribute("products", products);
                }
                // Forwards the request to the catalog JSP
                this.getServletContext().getRequestDispatcher("/catalog.jsp")
                        .forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the product for the given product id, if it is in the vector
     */

    private Product getProductItem(List<Product> products, String productIDToAdd) {
        for (Iterator<Product> iter = products.iterator(); iter.hasNext();) {
            Product item = iter.next();
            if (item.getProductID().equals(productIDToAdd)) {
                return item;
            }
        }
        return null;
    }

    public String getServletInfo() {
        return "The Catalog servlet adds products to the user's "
                + "cart and prints the catalog.";
    }
}
