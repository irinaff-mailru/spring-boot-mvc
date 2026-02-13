package dev.sorokin.springbootmvc.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseUserDto(
        Long id,
        String name,
        @NotNull @Email String email,
        Integer age,
        List<ResponsePetDto> pets
) {
}
