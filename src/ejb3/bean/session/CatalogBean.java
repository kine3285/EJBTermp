package src.ejb3.bean.session;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import src.ejb3.entity.Product;
import src.ejb3.logic.Catalog;

/**
 * This catalog Stateless Session Bean retrieves a list of productitems.
 * ProductItem has product Id, name of the product and description
 */
@Stateless
@Remote(Catalog.class)
public class CatalogBean implements Catalog {

    @PersistenceContext
    private EntityManager manager;
    
    public CatalogBean() {
    }

    public Product getProduct(String productId) {
        return manager.find(Product.class, productId);
    }

    public List<Product> getProductList() {
        /* find all products */
        return manager.createQuery("SELECT p FROM Product p")
                .getResultList();
    }

    public void addProduct(Product product) {
        manager.persist(product);
    }
    
}
