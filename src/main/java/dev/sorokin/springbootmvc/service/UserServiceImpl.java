package dev.sorokin.springbootmvc.service;

import dev.sorokin.springbootmvc.api.dto.RequestUpdateUserDto;
import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.api.dto.ResponseUserDto;
import dev.sorokin.springbootmvc.api.exception.AddUserException;
import dev.sorokin.springbootmvc.api.exception.NoPetFoundException;
import dev.sorokin.springbootmvc.api.exception.NoUserFoundException;
import dev.sorokin.springbootmvc.domain.entity.Pet;
import dev.sorokin.springbootmvc.domain.entity.User;
import dev.sorokin.springbootmvc.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AtomicLong id = new AtomicLong();
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Set<String> userNames = ConcurrentHashMap.newKeySet();

    private final UserMapper mapper;

    @Override
    public ResponseUserDto addUser(RequestUserDto dto) {
        if (!userNames.add(dto.name())) {
            throw new AddUserException("User with name %s already exists".formatted(dto.name()));
        }
        try {
            User newUser = mapper.toEntity(dto);
            newUser.setId(id.incrementAndGet());
            users.put(newUser.getId(), newUser);
            return mapper.toWeb(newUser);
        } catch (Exception e) {
            userNames.remove(dto.name());
            throw e;
        }
    }

    @Override
    public ResponseUserDto updateUser(Long id, RequestUpdateUserDto dto) {
        User user = findUserById(id);
        mapper.update(user, dto);
        return mapper.toWeb(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUserById(id);
        userNames.remove(user.getName());
        users.remove(id);
    }

    @Override
    public ResponseUserDto getUser(Long id) {
        return mapper.toWeb(findUserById(id));
    }

    @Override
    public void addPet(Long id, Pet pet) {
        User user = findUserById(id);
        user.getPets().add(pet);
    }

    @Override
    public void removePet(Long id, Pet pet) {
        User user = findUserById(id);
        boolean removed = user.getPets().remove(pet);
        if (!removed) {
            throw new NoPetFoundException("User %d does not have pet %d".formatted(user.getId(), pet.getId()));
        }
    }

    @Override
    public User findUserById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NoUserFoundException("User with id %d does not exists".formatted(id));
        }
        return user;
    }
}
