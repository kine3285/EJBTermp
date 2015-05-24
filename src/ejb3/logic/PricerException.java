package src.ejb3.logic;

/**
 * Exceptions thrown by Pricer
 */
public class PricerException extends Exception {

	public PricerException() {
		super();
	}

	public PricerException(Exception e) {
		super(e.toString());
	}

	public PricerException(String s) {
		super(s);
	}
}
