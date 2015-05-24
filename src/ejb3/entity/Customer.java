package src.ejb3.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This entity represents customer details.
 */
@Entity
public class Customer implements java.io.Serializable {

    private String customerID;

    private String name;

    private String password;

    private String address;
    
    private double discount;

    /**
     * Returns the Id of the customer
     */
    @Id
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Sets the id of the customer
     */
    public void setCustomerID(String customerId) {
        this.customerID = customerId;
    }
    /**
     * Returns the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the password of the customer
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the customer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public double getDiscount() {
        return discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public Customer() {
    }

    /**
     * Called when new data is created.
     *
     * We need to initialize our Bean's state with the parameters
     * passed from the client by calling our own abstract set
     * methods.  The Container can then inspect our Bean and
     * INSERT the corresponding database entries.
     */
    public void init(String id, String name, String password, String address) {
        setCustomerID(id);
        setName(name);
        setAddress(address);
        setPassword(password);
    }

}
