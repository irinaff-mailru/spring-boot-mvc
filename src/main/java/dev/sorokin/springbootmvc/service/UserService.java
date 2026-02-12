package dev.sorokin.springbootmvc.service;

import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    /**
     * Создание пользователя
     */
    User addUser(RequestUserDto dto);

    /**
     * Обновление пользователя
     */
    User updateUser(RequestUserDto dto);

    /**
     * Удаление пользователя
     */
    void deleteUser(@NotNull Long id);

    /**
     * Получение пользователя
     */
    User getUser(@NotNull Long id);
}
