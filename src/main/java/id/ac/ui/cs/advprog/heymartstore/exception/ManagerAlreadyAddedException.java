package id.ac.ui.cs.advprog.heymartstore.exception;

public class ManagerAlreadyAddedException extends IllegalArgumentException {
    public ManagerAlreadyAddedException(String email) {
        super("Manager with email " + email + " is already added.");
    }
}
