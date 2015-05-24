package src.ejb3.bean.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import src.ejb3.entity.*;


/**
 * This message-driven bean receives JMS messages
 * to process orders.  It then performs the order
 * processing.
 */
@MessageDriven(activationConfig = { 
    @ActivationConfigProperty(
            propertyName = "destinationType", 
            propertyValue = "javax.jms.Queue") 
    }) 
public class OrderProcessorBean implements MessageListener {

    @PersistenceContext
    EntityManager manager;
    
    public OrderProcessorBean() {        
    }
    
    /**
     * The one business method that message-driven beans have
     * Here we perform the actual order processing
     */
    public void onMessage(Message msg) {
        TextMessage tm = (TextMessage) msg;
        try {
            String orderID = tm.getText();
            System.out.println("Processing order " + orderID);

            Order order = manager.find(Order.class, orderID);

            /*
             * At this point, we could perform lots of tasks:
             *
             * - A call-out to a credit card company (perhaps through
             *   web services) to check the user's credit
             *   card rating and perform a debit.
             *
             * - Check the product inventory to ensure availability
             *
             * - Check the current backlog for shipping orders
             *
             * - Send an email confirmation
             *
             * In this example, we'll merely set the order status to
             * "approved".
             */
            order.setStatus(Order.Status.Approved);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e);
        }
    }

}
