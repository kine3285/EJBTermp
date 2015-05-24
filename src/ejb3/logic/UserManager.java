package src.ejb3.logic;

import java.util.List;

import src.ejb3.entity.Customer;

/**
 * This is the UserManager interface.
 */
public interface UserManager {

    /**
     * It uses the customer entity bean to crate a record in the databse
     */
    public Customer createUser(String customerId, String name, String password,
            String address);

    /**
     * Returns an user object for the given customer id. It uses customer entity
     * bean to retrieve the user record.
     */
    public Customer getUser(String customerId);

    /**
     * Authenticate the user.
     */
    public boolean validateUser(String login, String password)
            throws InvalidPasswordException;

    /**
     * Demo lookup
     * 
     * @return
     */
    public List<Customer> findAllCustomers();

    public void removeAllCustomers();
}
