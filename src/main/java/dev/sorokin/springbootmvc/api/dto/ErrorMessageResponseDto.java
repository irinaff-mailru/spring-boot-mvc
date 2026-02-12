package dev.sorokin.springbootmvc.api.dto;

import java.time.OffsetDateTime;

public record ErrorMessageResponseDto(
        int statusCode,
        String message,
        OffsetDateTime dateTime
) {
}
