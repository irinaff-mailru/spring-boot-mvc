package dev.sorokin.springbootmvc.api.controller;

import dev.sorokin.springbootmvc.api.dto.ErrorMessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value={Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageResponseDto handleException(Exception exception) {
        return new ErrorMessageResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                OffsetDateTime.now());
    }
}
