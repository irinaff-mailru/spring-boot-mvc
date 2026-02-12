package dev.sorokin.springbootmvc.api.dto;

import lombok.Builder;

@Builder
public record ResponsePetDto(
        Long id,
        String name,
        Long userId
) {
}
