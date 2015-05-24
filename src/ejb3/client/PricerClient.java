package src.ejb3.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import src.ejb3.bean.session.PricerBean;

/**
 * This class is an example of a standalone JAX-RPC client code which uses both
 * the static stub and the dynamic proxy approach to get a reference to the
 * remote Web Service
 */
public class PricerClient {
    static String host = "localhost";

    static String portType = "PricerBean";

    static String serviceName = "PricerService";

    static String serviceEndpointAddress = "http://" + host + ":8080/"
            + serviceName;

    static String nameSpace = "urn:session.impl.shop.examples";

    public static void main(String[] args) throws Exception {

        URL wsdlLocation = new URL(serviceEndpointAddress + "/" + portType
                + "?WSDL");
        QName serviceNameQ = new QName(nameSpace, serviceName);

        // dynamic service usage
        Service service = Service.create(wsdlLocation, serviceNameQ);
        PricerBean pricerPort = service.getPort(PricerBean.class);

        String user = "Gerald";

        System.out.println("Tax rate: " + pricerPort.getTaxRate());
        System.out.println("Discount for : " + user + " is "
                + pricerPort.getPersonalDiscountRate(user));

        System.out
                .println("Discount for 1 item (at $1000,- per piece) for " + user + " is "
                        + pricerPort.getDiscount(1, 1000, user));

        System.out
                .println("Discount for 5 items (at $1000,- per piece) for " + user + " is "
                        + pricerPort.getDiscount(5, 5000, user));

    }
}
