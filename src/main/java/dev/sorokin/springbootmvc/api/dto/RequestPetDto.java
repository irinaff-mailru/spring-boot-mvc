package dev.sorokin.springbootmvc.api.dto;

import lombok.Builder;

@Builder
public record RequestPetDto(
        String name,
        Long userId
) {
}
