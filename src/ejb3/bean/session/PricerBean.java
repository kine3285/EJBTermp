package src.ejb3.bean.session;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import src.ejb3.entity.Customer;
import src.ejb3.entity.LineItem;
import src.ejb3.logic.Pricer;
import src.ejb3.logic.UserManager;

/**
 * Stateless Session Bean which computes prices based
 * upon a set of pricing rules.  The pricing rules are
 * deployed with the bean as environment properties.
 */
@Stateless
@Remote(Pricer.class)
@WebService(serviceName="PricerService", portName="PricerPort")
public class PricerBean implements Pricer {

    @Resource(name="taxRate")
    public int taxRate = 0;
    
    @Resource(name="bulkDiscountRate")
    public int bulkDiscountRate = 0;
    
    @EJB
    UserManager userManager;
    
    public PricerBean() {        
    }
    
    /**
     * bulk discounts apply to quantities of BULK or more items
     */
    private static final int BULK = 5;

    /**
     * This method computes the applicable discount in absolute
     * figure, based on bulk and personal discounts that may apply.
     * 
     * @param quantity the number of items that a user intends to buy
     * @param basePrice the overall, non-discounted volume of the
     *        purchase (individual price times quantity)
     * @param the user name
     * @return the subTotal for the line item after applying any
     *         applicable discounts, excluding taxes
     */
    public double getDiscount(int quantity, double basePrice, String user) {
        double discountRate = getPersonalDiscountRate(user);
        if (quantity >= BULK) {
            discountRate += getBulkDiscountRate();
            System.out.println("Using getBulkDiscountRate " + getBulkDiscountRate());
        }
       
        /*
         * Calculate the discount in absolute figures
         */
        return basePrice * (discountRate / 100);
    }

    /**
     * A bulk discount applies to quantities of more than 5 pieces.
     * @return the bulk discount rate int percent
     */
    public double getBulkDiscountRate() {
        return this.bulkDiscountRate;
    }

    /**
     * Customers with certain names get discounts.  The discount rules
     * are stored in the environment properties that the bean is
     * deployed with.  
     */
    public double getPersonalDiscountRate(String userName) {
        /*
         * Get the name of this customer.
         */
        Customer user = userManager.getUser(userName);
        if( user != null)
            return user.getDiscount();
        else
            return 0;
    }

    /**
     * Computes the subtotal price for a set of products the customer
     * is interested in.  The subtotal takes into account the price of
     * each product the customer wants, the quantity of each product,
     * and any personal discounts the customer gets.  However, the
     * subtotal ignores taxes.
     *
     * @param quote All the data needed to compute the
     * subtotal is in this parameter.
     */
    public double priceSubtotal(String user, List<LineItem> items) {
        System.out.println("PricerBean.priceSubtotal() called");

        /*
         * Compute the subtotal
         */
        double subTotal = 0;
        
            for(Iterator<LineItem> iter = items.iterator(); iter.hasNext(); ) {
                LineItem item = iter.next();
                item.setDiscount(
                    getDiscount(item.getQuantity(), item.basePrice(),user));

                /*
                 * Add the price to the subtotal.
                 */
                subTotal += (item.basePrice() - item.getDiscount());
            }

        return subTotal;
    }

    /**
     * Computes the taxes on a quote.
     */
    public double priceTaxes(double subtotal) {
        System.out.println("PricerBean.priceTaxes() called, taxes: " + getTaxRate());
            return (getTaxRate() / 100) * subtotal;
    }

    /**
     * @return the applicable tax rate
     */
    public double getTaxRate() {
        return taxRate;
    }
}
