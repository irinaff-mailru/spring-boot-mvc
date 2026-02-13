package dev.sorokin.springbootmvc.api.controller;

import dev.sorokin.springbootmvc.api.dto.RequestPetDto;
import dev.sorokin.springbootmvc.api.dto.ResponsePetDto;
import dev.sorokin.springbootmvc.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/add")
    public ResponseEntity<ResponsePetDto> add(@RequestBody @Valid RequestPetDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(petService.addPet(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePetDto> update(@PathVariable @Positive Long id,
                                                  @RequestParam Long ownerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(petService.updatePet(id, ownerId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        petService.deletePet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponsePetDto> get(@PathVariable @Positive Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(petService.getPet(id));
    }
}
