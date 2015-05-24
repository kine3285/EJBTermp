package src.ejb3.logic;

import java.util.List;

import src.ejb3.entity.LineItem;

/**
 * These are the business logic methods exposed publicly by the
 * Pricer component, a function that computes a price for a 
 * given user and base price. 
 */

public interface Pricer {
    /**
     * Computes the price of a set of goods
     */

    public double priceSubtotal(String user, List<LineItem> items);
    
    public double priceTaxes(double subtotal);
    
    /**
     * @return the applicable tax rate
     */
    double getTaxRate();

    /**
     * @return the current discount rate for buying lots of items
     */
    double getBulkDiscountRate();

    /**
     * @return the discount rate for a given user in percent 
     */
    double getPersonalDiscountRate(String userName);

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
    double getDiscount(int quantity, double basePrice, String user);

}
