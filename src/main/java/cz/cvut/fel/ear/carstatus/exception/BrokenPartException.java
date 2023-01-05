package cz.cvut.fel.ear.carstatus.exception;

public class BrokenPartException extends EarException{
    public BrokenPartException() {
    }

    public BrokenPartException(String message) {
        super(message);
    }

    public BrokenPartException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrokenPartException(Throwable cause) {
        super(cause);
    }
}
