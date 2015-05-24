package src.ejb3.logic;

/**
 * Exceptions thrown by usermanager
 */
public class InvalidPasswordException extends Exception {

	public InvalidPasswordException() {
		super();
	}

	public InvalidPasswordException(Exception e) {
		super(e.toString());
	}

	public InvalidPasswordException(String s) {
		super(s);
	}
}