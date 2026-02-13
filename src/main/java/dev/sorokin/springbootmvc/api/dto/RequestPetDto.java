package dev.sorokin.springbootmvc.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestPetDto(
        @NotNull @NotBlank @Size(min = 5, max = 250) String name,
        @NotNull Long userId
) {
}
