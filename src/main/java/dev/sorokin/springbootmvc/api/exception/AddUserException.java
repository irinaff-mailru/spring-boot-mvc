package dev.sorokin.springbootmvc.api.exception;

public class AddUserException extends RuntimeException {
    public AddUserException(String message) {
        super(message);
    }
}
