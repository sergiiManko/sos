package pl.atins.exception;

/**
 * @author "Serhii Manko"
 */
public class UserAccountLockedException extends RuntimeException {
    public UserAccountLockedException(String message) {
        super(message);
    }

}
