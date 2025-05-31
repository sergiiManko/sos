package pl.atins.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }

}
