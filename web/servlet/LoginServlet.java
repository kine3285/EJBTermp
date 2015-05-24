package examples.shop.web.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import examples.shop.logic.Cart;
import examples.shop.logic.UserManager;

/**
 * This is the very first servlet the client deals with. It's a Login
 * authentication servlet and asks the user for his name and password, 
 * and pass it to the UserManager stateless session bean for verificatiion.
 * 
 * If the user authenticates properly, a reference to a new Cart is saved 
 * in his HttpSession object, and the user can begin to add items to his 
 * cart and shop around.
 */
public class LoginServlet extends HttpServlet {

    /** the user manager used to authenticate the user */
    @EJB
    UserManager userManager;

    /** the user's cart object */
    @EJB
    Cart cart;

    /**
     * The servlet engine calls this method once to initialize a servlet
     * instance.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            /* 
             * Get the initial context using the above startup params.
             */
            
            Context ctx = new InitialContext();
            userManager = (UserManager) ctx.lookup(UserManager.class
                    .getName());            
            cart = (Cart) ctx.lookup(Cart.class.getName());

        } catch (Exception e) {
            log(e);
            throw new ServletException(e.toString());
        }
    }

    /**
     * The servlet engine calls this method when the user's desktop browser
     * sends an HTTP request.
     */
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * Set up the user's HttpSession
         */
        HttpSession session = request.getSession(true);

        System.out.println(request.getAttributeNames().toString());
        /*
         * Retrieve the login name / password from the URL string.
         */
        String loginName = request.getParameter("Login");
        String password = request.getParameter("Password");
        boolean isLogin = false;

        /*
         * If user has not tried to log in yet, present him with the login
         * screen.
         */
        if ((loginName == null) || (password == null)) {
            writeForm(request, response, false);
            return;
        } else {
            /*
             * Otherwise, the user has been to this screen already, and has
             * entered some information. Verify that information.
             */
            try {
                isLogin = userManager.validateUser(loginName, password);
            } catch (Exception e) {
                writeForm(request, response, true);
                e.printStackTrace();
                return;
            }
            /*
             * If the passwords match, make a new Cart Session Bean, and add it
             * to the user's HttpSession object. When the user navigates to
             * other servlets, the other servlets can access the HttpSession to
             * get the user's Cart.
             */
            if (isLogin) {
                try {
                    cart.setOwner(loginName);
                    cart.clear();
                    session.setAttribute("cart", cart);

                    /*
                     * Call the main page
                     */
                    RequestDispatcher disp = this.getServletContext()
                            .getRequestDispatcher("/wsf.jsp");
                    disp.forward(request, response);

                    return;
                } catch (Exception e) {
                    log(e);
                    throw new ServletException(e.toString());
                }
            } else
                writeForm(request, response, true);
        }

        /*
         * If there was no match, the user is not authenticated. Present another
         * login screen to him, with an error message indicating that he is not
         * authenticated.
         */
        writeForm(request, response, true);
    }

    /**
     * Writes the Login Screen (private use only)
     * 
     * @param showError
     *            true means show an error b/c client was not authenticated last
     *            time.
     */
    private void writeForm(HttpServletRequest request,
            HttpServletResponse response, boolean showError)
            throws ServletException, IOException {

        /*
         * Set a variable indicating whether or not we failed to log-in. The JSP
         * will read this variable.
         */
        request.setAttribute("loginFailed", new Boolean(showError));

        /*
         * Forward the request to the login JSP
         */
        RequestDispatcher disp = this.getServletContext().getRequestDispatcher(
                "/login.jsp");
        disp.forward(request, response);
    }

    private void log(Exception e) {
        e.printStackTrace();
    }

    public String getServletInfo() {
        return "The Login servlet verifies a user.";
    }
}
