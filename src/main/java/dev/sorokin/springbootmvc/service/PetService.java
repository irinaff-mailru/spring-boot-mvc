package dev.sorokin.springbootmvc.service;

import dev.sorokin.springbootmvc.api.dto.RequestPetDto;
import dev.sorokin.springbootmvc.domain.entity.Pet;
import jakarta.validation.constraints.NotNull;

public interface PetService {

    /**
     * Создание пользователя
     */
    Pet addPet(RequestPetDto dto);

    /**
     * Обновление пользователя
     */
    Pet updatePet(RequestPetDto dto);

    /**
     * Удаление пользователя
     */
    void deletePet(@NotNull Long id);

    /**
     * Получение пользователя
     */
    Pet getPetr(@NotNull Long id);
}
