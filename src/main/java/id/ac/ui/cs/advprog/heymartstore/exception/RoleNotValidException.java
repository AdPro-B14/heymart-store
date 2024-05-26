package id.ac.ui.cs.advprog.heymartstore.exception;

public class RoleNotValidException extends IllegalAccessException {
    public RoleNotValidException() {
        super("You hava no access.");
    }
}
