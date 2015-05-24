package src.ejb3.logic;

import java.util.List;

import src.ejb3.entity.LineItem;

/**
 * The Cart interface.
 */
public interface Cart {

    /**
     * Adds an item to the shopping cart
     */
    public void add(LineItem lineItem);
    
    /**
     * Changes the quantities of contents in the shopping cart
     */
    public void modify(String productID, int quantity) 
        throws Exception;

    /**
     * Returns all the shopping cart line items
     * 
     * @return A collection of Cart LineItems
     */
    public List<LineItem> getAll();

    /**
     * Empties the shopping cart
     */
    public void clear();

    /**
     * Get/set methods for the shopping cart owner's name
     */
    public String getOwner();
    
    public void setOwner(String owner);

    /**
     * Purchases this cart. The cart will create an order in the database.
     * 
     * @return The Order confirmation number
     */
    public String purchase();    
    
   /**
     * Returns the subtotal price which has been previously set by
     * setSubtotal().
     * 
     * @return the subtotal of this cart items.
     */ 
    public double getSubtotal();

   /**
     * Sets the subtotal price. Our external pricer bean is responsible for
     * calculating the subtotal. It calculates it based upon customer discounts
     * (and can be extended to include other rules as well).
     */
    public void setSubtotal(double subTotal);
    
  /**
     * Returns the taxes for this Quote.
     */
    public double getTaxes();
    
    /**
     * Sets the taxes for this Quote.
     */
    public void setTaxes(double taxes);    
    
    /**
     * Returns the total price. Total Price is computed from: 1) Subtotal price
     * 2) Tax
     */	
    public double getTotalPrice();
   

}
