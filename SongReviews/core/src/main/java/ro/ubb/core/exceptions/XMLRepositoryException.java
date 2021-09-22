package ro.ubb.core.exceptions;

public class XMLRepositoryException extends ProgramException {
    public XMLRepositoryException(String message) {
        super(message);
    }

    public XMLRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLRepositoryException(Throwable cause) {
        super(cause);
    }
}
