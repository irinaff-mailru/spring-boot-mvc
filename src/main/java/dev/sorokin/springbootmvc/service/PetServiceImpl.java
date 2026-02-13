package dev.sorokin.springbootmvc.service;

import dev.sorokin.springbootmvc.api.dto.RequestPetDto;
import dev.sorokin.springbootmvc.api.dto.ResponsePetDto;
import dev.sorokin.springbootmvc.api.exception.NoPetFoundException;
import dev.sorokin.springbootmvc.domain.entity.Pet;
import dev.sorokin.springbootmvc.domain.entity.User;
import dev.sorokin.springbootmvc.service.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final AtomicLong id = new AtomicLong();
    private final Map<Long, Pet> pets = new ConcurrentHashMap<>();

    private final UserService userService;
    private final PetMapper mapper;

    @Override
    public ResponsePetDto addPet(RequestPetDto dto) {
        User owner = userService.findUserById(dto.userId());

        Pet newPet = mapper.toEntity(dto);
        newPet.setId(id.incrementAndGet());
        newPet.setOwner(owner);

        pets.put(newPet.getId(), newPet);
        userService.addPet(owner.getId(), newPet);

        return mapper.toWeb(newPet);
    }

    @Override
    public ResponsePetDto updatePet(Long id, Long ownerId) {
        Pet existingPet = findPetById(id);
        User oldOwner = existingPet.getOwner();
        User newOwner = userService.findUserById(ownerId);

        if (!oldOwner.getId().equals(newOwner.getId())) {
            userService.removePet(oldOwner.getId(), existingPet);
            userService.addPet(newOwner.getId(), existingPet);
            existingPet.setOwner(newOwner);
        }

        return mapper.toWeb(existingPet);
    }

    @Override
    public void deletePet(Long id) {
        Pet pet = findPetById(id);
        pets.remove(pet.getId());
        userService.removePet(pet.getOwner().getId(), pet);
    }

    @Override
    public ResponsePetDto getPet(Long id) {
        return mapper.toWeb(findPetById(id));
    }

    private Pet findPetById(Long id) {
        Pet pet = pets.get(id);
        if (pet == null) {
            throw new NoPetFoundException("Pet with id %d not exists".formatted(id));
        }
        return pet;
    }
}
