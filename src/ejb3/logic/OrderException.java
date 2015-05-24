package src.ejb3.logic;

/**
 * Exceptions thrown by Order
 */
public class OrderException extends Exception {

	public OrderException() {
		super();
	}

	public OrderException(Exception e) {
		super(e.toString());
	}

	public OrderException(String s) {
		super(s);
	}
}
