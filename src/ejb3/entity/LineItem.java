package src.ejb3.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 * A line item entity is a quantity of a single product. 
 * 
 * Instances of this class are used for both in-memory represenation 
 * of carts and the persistent representation of orders in 
 * the database 
 */
@Entity
public class LineItem implements java.io.Serializable {

    private String id;
    
    /** the product */
    private Product product;

    /** Number of items */
    private int quantity;

    /** Amount of the discount for each item */
    private double discount;

    /**
     * Constructor
     * @param productitem
     * @param number of items
     * @Param discount for each item 
     */
    public LineItem(Product productItem, int quantity, double discount) {
        System.out.println("LineItem(...) called");
        this.product = productItem;
        this.quantity = quantity;
        this.discount = discount;
    }

    public LineItem() {
        System.out.println("New LineItem created.");
    }
    
    /**
     * The id number of this line item. This is our primary key as well.
     * 
     * @return the id
     */
    @Id
    public String getId() {
        return id;
    }

    /**
     * Sets the id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * @return the productitem.
     * productitem has product id, name of the product and description
     */
    @OneToOne
    public Product getProduct() {
        System.out.println("LineItem.getProduct() called.");
        return product;
    }

    /**
     * @param productItem.
     */
    public void setProduct(Product product) {
        System.out.println("LineItem.setProduct() called.");
        this.product = product;
    }

    /**
     * @return the number of items.
     */
    public int getQuantity() {
        System.out.println("LineItem.getQuantity() called.");
        return quantity;
    }

    /**
     * @param the number of items.
     */
    public void setQuantity(int quantity) {
        System.out.println("LineItem.setQuantity() called.");
        this.quantity = quantity;
    }

    /**
     * @return the base price.  The base price is the
     * product's price times the quantity ordered.  This
     * figure does not take discounts into consideration.
     */
    public double basePrice() {
        System.out.println("LineItem.getBasePrice() called.");
        return quantity * product.getBasePrice();
    }

    /**
     * @return the discount that the customer gets on
     * this order.
     *
     * Note: The discount is a whole number, not
     *       a percentage discount.
     */
    public double getDiscount() {
        System.out.println("LineItem.getDiscount() called.");
        return discount;
    }

    /**
     * @param the discount that the customer gets on
     * this order.
     *
     * Note: The discount is a whole number, not
     *       a percentage discount.
     */
    public void setDiscount(double discount) {
        System.out.println("LineItem.setDiscount(" + discount + ") called.");
        this.discount = discount;
    }

}
