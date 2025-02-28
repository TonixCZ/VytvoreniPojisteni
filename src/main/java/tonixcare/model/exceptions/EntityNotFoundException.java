package tonixcare.model.exceptions;

public class EntityNotFoundException extends RuntimeException {

    // Konstruktor bez parametru
    public EntityNotFoundException() {
        super();
    }

    // Konstruktor s parametrem pro detailní zprávu
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Konstruktor s parametrem pro detailní zprávu a původní výjimku (stacktrace)
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
