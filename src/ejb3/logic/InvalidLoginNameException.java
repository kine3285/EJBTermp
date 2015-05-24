package src.ejb3.logic;

/**
 * Exceptions thrown by usermanager
 */
public class InvalidLoginNameException extends Exception {

	public InvalidLoginNameException() {
		super();
	}

	public InvalidLoginNameException(Exception e) {
		super(e.toString());
	}

	public InvalidLoginNameException(String s) {
		super(s);
	}
}
