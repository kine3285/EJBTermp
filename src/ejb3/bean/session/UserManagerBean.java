package src.ejb3.bean.session;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import src.ejb3.entity.Customer;
import src.ejb3.logic.InvalidPasswordException;
import src.ejb3.logic.UserManager;

/**
 * UserManager is Stateless session bean resposible for creating and
 * retrieving a customer record.  It also authenticates the user.
 */

@Stateless
@Remote(UserManager.class)
public class UserManagerBean implements UserManager {

    @PersistenceContext
    EntityManager manager;

    public UserManagerBean() {
    }
    
    /**
     * Returns an customer object for the given customer id.
     */
    public Customer getUser(String customerId) {
        return manager.find(Customer.class, customerId);       
    }

    /**
     * It uses the customer entity bean to create a record in the databse
     * @param customerId 
     * @param name 
     * @param password 
     * @param address 
     */

    public Customer createUser(String customerId, String name, String password, String address) {        
        Customer customer = new Customer();
        customer.init(customerId, name, password, address);
        manager.persist(customer);
        return customer;
    }

    /**
     * This method authenticates the user
     *
     * @return true, if the password is correct
     * @throws an InvalidPasswordException if password is incorrect.
     */

    public boolean validateUser(String customerID, String password)
        throws InvalidPasswordException {
        if(customerID== null || password == null)
            throw new IllegalArgumentException("id " + customerID + " pw " + password);
        Customer user = getUser(customerID);
        if (user != null && password.equals(user.getPassword())) {
            return true;
        } else {
            System.out.println("Failure to validate user ID " + customerID
                    + " with password " + password + " against password "
                    + user.getPassword());
            
            throw new InvalidPasswordException("Invalid Password:"
                    + password);
        }
    }
    
    public List<Customer> findAllCustomers() {
        return manager.createQuery("SELECT c FROM Customer c").getResultList();
    }
    
    public void removeAllCustomers() {
        List l = manager.createQuery("SELECT c FROM Customer c ").getResultList();
        for(Iterator iter = l.iterator(); iter.hasNext();) {
            manager.remove(iter.next());
        }
    }
}
