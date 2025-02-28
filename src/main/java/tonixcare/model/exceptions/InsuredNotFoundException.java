package tonixcare.model.exceptions;

public class InsuredNotFoundException extends RuntimeException {

    public InsuredNotFoundException() {
        super();
    }

    public InsuredNotFoundException(String message) {
        super(message);
    }

    public InsuredNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
