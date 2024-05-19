package id.ac.ui.cs.advprog.heymartstore.exception;

public class ManagerRegistrationFailedException extends RuntimeException {
    public ManagerRegistrationFailedException(String email) {
        super("Manager account registration with email " + email + " failed. Try another email.");
    }
}
