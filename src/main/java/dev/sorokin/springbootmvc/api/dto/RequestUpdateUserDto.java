package dev.sorokin.springbootmvc.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestUpdateUserDto(
        @NotNull @Email @Size(min = 5, max = 250) String email,
        @NotNull @Size(min = 15, max = 150) Integer age
) {
}
