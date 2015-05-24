package src.ejb3.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity 
 *
 * This is a product that's persistent.  It has an ID #, a name,
 * a description, and a base price.
 */
@Entity
public class Product implements java.io.Serializable {

    public String name;
    public String description;
    public double basePrice;
    public String productID;
    
    public Product() {
    }

    /**
     * @return the product id.
     */
    @Id
    public String getProductID() {
        return productID;
    }

    /**
     * Sets the product id.
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the base price of the product.
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the base price of the product.
     */
    public void setBasePrice(double price) {
        this.basePrice = price;
    }

    
    /**
     * This is the initialization method
     */
    public Product init(String productID, String name, String description,
            double basePrice) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        return this;
    }

}
