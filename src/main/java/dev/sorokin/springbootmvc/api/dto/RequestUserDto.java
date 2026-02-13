package dev.sorokin.springbootmvc.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record RequestUserDto(
        @NotNull @NotBlank @Size(min = 5, max = 250) String name,
        @NotNull @Email @Size(min = 5, max = 250) String email,
        @NotNull @Size(min = 15, max = 150) Integer age
) {
}
