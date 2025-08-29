package nila;

/**
 * Represents an exception specific to the {@code Nila} chatbot application.
 * This exception is thrown when an invalid operation, command,
 * or input is encountered during program execution.
 */
public class NilaException extends Exception {
    public NilaException(String message) {
        super(message);
    }
}
