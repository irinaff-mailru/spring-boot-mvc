package dev.sorokin.springbootmvc.api.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import dev.sorokin.springbootmvc.api.dto.ErrorMessageResponseDto;
import dev.sorokin.springbootmvc.api.exception.AddPetException;
import dev.sorokin.springbootmvc.api.exception.AddUserException;
import dev.sorokin.springbootmvc.api.exception.NoPetFoundException;
import dev.sorokin.springbootmvc.api.exception.NoUserFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AddUserException.class, AddPetException.class})
    public ResponseEntity<ErrorMessageResponseDto> handleNotAcceptable(Exception exception) {
        log.error("Unhandled exception occurred: ", exception);
        return buildErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler({NoUserFoundException.class, NoPetFoundException.class})
    public ResponseEntity<ErrorMessageResponseDto> handleNotFound(Exception exception) {
        log.error("Unhandled exception occurred: ", exception);
        return buildErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessageResponseDto> handleNotValid(Exception exception) {
        log.error("Unhandled exception occurred: ", exception);
        return buildErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponseDto> handleOther(Exception exception) {
        log.error("Unhandled exception occurred: ", exception);
        return buildErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ErrorMessageResponseDto> buildErrorMessage(HttpStatus status, String message) {
        ErrorMessageResponseDto error = new ErrorMessageResponseDto(
                status.value(),
                message,
                OffsetDateTime.now());
        return new ResponseEntity<>(error, status);
    }
}
