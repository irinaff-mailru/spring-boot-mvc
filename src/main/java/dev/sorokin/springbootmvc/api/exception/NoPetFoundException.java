package dev.sorokin.springbootmvc.api.exception;

public class NoPetFoundException extends RuntimeException {
    public NoPetFoundException(String message) {
        super(message);
    }
}
