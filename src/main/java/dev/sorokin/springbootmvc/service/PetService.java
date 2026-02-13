package dev.sorokin.springbootmvc.service;

import dev.sorokin.springbootmvc.api.dto.RequestPetDto;
import dev.sorokin.springbootmvc.api.dto.ResponsePetDto;
import jakarta.validation.constraints.NotNull;

public interface PetService {

    /**
     * Создание питомца
     */
    ResponsePetDto addPet(@NotNull RequestPetDto dto);

    /**
     * Обновление питомца
     */
    ResponsePetDto updatePet(@NotNull Long id, @NotNull Long ownerId);

    /**
     * Удаление питомца
     */
    void deletePet(@NotNull Long id);

    /**
     * Получение питомца
     */
    ResponsePetDto getPet(@NotNull Long id);
}
