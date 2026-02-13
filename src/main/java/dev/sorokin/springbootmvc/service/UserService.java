package dev.sorokin.springbootmvc.service;

import dev.sorokin.springbootmvc.api.dto.RequestUpdateUserDto;
import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.api.dto.ResponseUserDto;
import dev.sorokin.springbootmvc.domain.entity.Pet;
import dev.sorokin.springbootmvc.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    /**
     * Создание пользователя
     */
    ResponseUserDto addUser(@NotNull RequestUserDto dto);

    /**
     * Обновление пользователя
     */
    ResponseUserDto updateUser(@NotNull Long id, RequestUpdateUserDto dto);

    /**
     * Удаление пользователя
     */
    void deleteUser(@NotNull Long id);

    /**
     * Получение пользователя
     */
    ResponseUserDto getUser(@NotNull Long id);

    User findUserById(Long id);

    public void addPet(Long id, Pet pet);

    public void removePet(Long id, Pet pet);
}
