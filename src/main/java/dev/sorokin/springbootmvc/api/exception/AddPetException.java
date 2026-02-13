package dev.sorokin.springbootmvc.api.exception;

public class AddPetException extends RuntimeException {
    public AddPetException(String message) {
        super(message);
    }
}
