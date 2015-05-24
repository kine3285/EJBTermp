package src.ejb3.logic;

import java.util.*;

import src.ejb3.entity.Product;

/**
 * This is the Catalog business interface.
 */

public interface Catalog  {
    
	/**
     * @return a vector of Products
     */
    public List<Product> getProductList();
    
    /**
     * @return a Product for the given product id.
     */
    public Product getProduct(String productId);
    
    /**
     * Add a new product to the catalog
     * @param product
     */
    public void addProduct(Product product);
        
}


