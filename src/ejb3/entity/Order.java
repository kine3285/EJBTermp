package src.ejb3.entity;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This entity represents an order placed for goods. 
 * 
 * Note: This entity could easily be extended to include other things, such as:
 * <ul>
 * <li>Shipping charges 
 * <li>Shipping address vs. Billing address 
 * <li>A date which this order is scheduled to be completed/shipped.
 * </ul>
 */

@Entity
@Table(name = "ORDERS")
public class Order {

    public enum Status {
        Submitted, Unverified, Approved, Shipping, Delivered, Returned
    };

    private String orderId;

    private List<LineItem> lineItems;

    private Customer customer;

    private Timestamp orderDate;

    private Status status;

    private double taxes;

    private double subTotal;

    /**
     * This order's identification number. It's also our Primary Key.
     * 
     * @return order id
     */
    @Id
    public String getOrderID() {
        return orderId;
    }

    /**
     * Sets the order id of an order
     */
    public void setOrderID(String id) {
        orderId = id;
    }

    /**
     * This represents a one-to-many relationship to the
     * line items in the order. Persisting the order cascades
     * to the line items. 
     *  
     * @return the set of order line items
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    /**
     * Sets set of the order line items
     */
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    /**
     * @returns the Customer who placed this Order.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * set customer who placed this order.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the date this order was placed.
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date this order
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return status information about the order.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status information about the order.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Sets the subtotal of this order
     */

    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Returns the subtotal of this order
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Returns taxes of this order
     */
    public double getTaxes() {
        return taxes;
    }

    /**
     * Sets the taxes of this order
     */
    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    /**
     * Returns the subtotal rice of the entire order, ie. the sum of 
     * the base prices of all line items in this order. 
     */
    public double totalPrice() {
        double totalPrice = 0;
        for (Iterator<LineItem> iter = getLineItems().iterator(); iter
                .hasNext();) {
            LineItem item = iter.next();
            totalPrice += item.getProduct().basePrice;
        }
        return totalPrice;
    }

    /**
     * Called to initialize a new order entity 
     * 
     * We need to initialize our entity's state with the parameters passed from
     * the client.
     */
    public void init(String orderID, Customer customer, Status status,
            double subTotal, double taxes) {
        System.out.println("Order.init(" + orderID + ") called");
        setOrderID(orderID);
        setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
        setStatus(status);
        setSubTotal(subTotal);
        setTaxes(taxes);
    }

}
