package src.ejb3.client;

import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import src.ejb3.entity.Product;
import src.ejb3.logic.Catalog;
import src.ejb3.logic.UserManager;

/**
 * Client test application on a CMP Entity Bean, Product.
 */
public class SetupClient {

    public static void main(String[] args) throws Exception {

        try {

            Context ctx = getInitialContext("t3://localhost:7001", "weblogic", "weblogic123");
            Catalog catalog = (Catalog) ctx.lookup("Catalog");

            /*
             * Use the catalog to create Products
             */
            catalog.addProduct(new Product().init("123-456-7890", "P4-1.8",
                    "1.8 GHz Pentium 4", 200));
            catalog.addProduct(new Product().init("123-456-7891", "P4-3",
                    "3 GHz Pentium 4", 300));
            catalog.addProduct(new Product().init("123-456-7892", "P4-4",
                    "4 GHz Pentium", 400));
            catalog.addProduct(new Product().init("123-456-7893", "SD-256",
                    "256 MB SDRAM", 50));
            catalog.addProduct(new Product().init("123-456-7894", "SD-512",
                    "512 MB SDRAM", 100));
            catalog.addProduct(new Product().init("123-456-7895", "DD-1000",
                    "1GB MB DDRAM", 200));            
            catalog.addProduct(new Product().init("123-456-7896", "MP3-x",
                    "MP3 Player", 200));

            /*
             * Find a Product, and print out it's description
             */
            for (Iterator<Product> i = catalog.getProductList().iterator(); i
                    .hasNext();) {
                System.out.println(i.next().getDescription());
            }
            
            UserManager userManager = 
                (UserManager) ctx.lookup(UserManager.class.getName());            
            userManager.createUser("Gerald", "Gerald Brose", "password",
            "D-13509 Berlin, Germany");            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Context getInitialContext(String url, String username, String password)
        throws NamingException
        {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            env.put(Context.PROVIDER_URL, url);
            env.put(Context.SECURITY_PRINCIPAL, username);
            env.put(Context.SECURITY_CREDENTIALS, password);

            return new InitialContext(env);
        }
}
