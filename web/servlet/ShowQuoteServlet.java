package examples.shop.web.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

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
import examples.shop.logic.Cart;
import examples.shop.logic.Pricer;

/**
 * This servlet allows the user to view and modify his current selections.
 * 
 * By the time this servlet is called, the user has logged in (via the Login
 * servlet), and has a shopping cart started (i.e. cart bean). Since this
 * servlet is pooled and re-used for different user requests, the servlet code
 * does not store any information specific to any user. Rather, we store a
 * reference to the user's cart in the user's HttpSession object, which is
 * globally accessible to all servlets.
 */
public class ShowQuoteServlet extends HttpServlet {

    // Pricer for pricing the cart
    private Pricer pricer;

    /**
     * The servlet engine calls this method once to initialize a servlet
     * instance.
     * 
     * In the body of this method, we need to acquire a Pricer EJB Object for
     * pricing the carts.
     */
    public void init(ServletConfig config) throws ServletException {
        /*
         * Call parent to store the config object, so that getServletConfig()
         * can return it.
         */
        super.init(config);
        try {
            Context ctx = new InitialContext();
            pricer = (Pricer)ctx.lookup(Pricer.class.getName());
        } catch (NamingException e) {
            e.printStackTrace();
        }        
    }

    /**
     * The servlet engine calls this method when the user's desktop browser
     * sends an HTTP GET request.
     */
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * Get the user's HttpSession, and from that get the user's current
         * cart.
         */
        HttpSession session = request.getSession(false);
        if (session == null) {
            /*
             * Redirect user to login page if no session
             */
            response.sendRedirect(response.encodeRedirectURL("/jasmine/login"));
            return;
        }

        Object obj = session.getAttribute("cart");
        if (obj == null) {
            /*
             * Redirect user to login page if no session
             */
            response.sendRedirect(response.encodeRedirectURL("/jasmine/login"));
            return;
        }

        Cart cart = (Cart) obj;

        /*
         * If the user clicked the 'Order' button, he wants to purchase his
         * selections. We forward the user to the servlet that handles
         * purchasing.
         */
        if (request.getParameter("Order") != null) {
            /*
             * First, turn the cart into an order
             */
            String orderId = cart.purchase();
            /*
             * Stick the orderID in the request so the JSP gets it
             */
            request.setAttribute("orderID", orderId);
            cart.clear();
            this.getServletContext().getRequestDispatcher("/receipt.jsp")
                    .forward(request, response);
            return;
        }

        /*
         * Next, we need to figure out what button the user clicked, if any.
         * These come to us as form parameters. We need to loop through each
         * parameter and interpret it.
         */
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);

            /*
             * If user clicked 'Update' button, then the user wants to change
             * the quantities of each product he is ordering. We'll process
             * those quantities below.
             */
            if (paramName.equals("Update")) {
            } else if (paramName.equals("Clear")) {
                /*
                 * The user wants to clear the form
                 */
                cart.clear();
                this.getServletContext()
                        .getRequestDispatcher("/clearQuote.jsp").forward(
                                request, response);
                return;
            } else {
                /*
                 * If the parameter represents a quantity of a particular
                 * product the user is interested in, then we should update that
                 * product's quantity to reflect this new value.
                 */
                try {
                    /*
                     * Convert the quantity to int format, and set the new
                     * quantity
                     */
                    int quantity = Integer.parseInt(paramValue);
                    cart.modify(paramName, quantity);
                } catch (NumberFormatException e) {
                    throw new ServletException("Bad parameter to servlet: "
                            + paramName + ", " + paramValue);
                } catch (Exception e) {
                    throw new ServletException(e.toString());
                }
            }
        }

        /*
         * Recalculate all totals based upon new quantities
         */
        try {
            cart.setSubtotal(pricer.priceSubtotal(cart.getOwner(), cart.getAll()));
            cart.setTaxes(pricer.priceTaxes(cart.getSubtotal()));
        } catch (Exception e) {
            log(e);
            throw new ServletException(e.toString());
        }

        /*
         * Otherwise, show the current cart again
         */
        List<LineItem> lineItems = cart.getAll();
        if (lineItems.size() > 0) {
            /*
             * Stick lineitems, subtotal, taxes and total in request
             */
            request.setAttribute("lineItems", lineItems);
            request.setAttribute("subTotal", new Double(cart.getSubtotal()));
            request.setAttribute("taxes", new Double(cart.getTaxes()));
            request.setAttribute("taxRate", new Double(pricer.getTaxRate()));
            request.setAttribute("total", new Double(cart.getTotalPrice()));
            // Forwards the request to the showQuote JSP.
            this.getServletContext().getRequestDispatcher("/showQuote.jsp")
                    .forward(request, response);
            return;
        } else {
            /*
             * If there are no products, print out that the cart is empty.
             */
            this.getServletContext().getRequestDispatcher("/emptyQuote.jsp")
                    .forward(request, response);
            return;
        }
    }

    private void log(Exception e) {
        e.printStackTrace();
    }

    public String getServletInfo() {
        return "The ShowQuote servlet returns information about"
                + "the products that the user is in the process of ordering.";
    }
}
