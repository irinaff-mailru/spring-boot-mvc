package dev.sorokin.springbootmvc.api.controller;

import dev.sorokin.springbootmvc.api.dto.RequestUpdateUserDto;
import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.api.dto.ResponseUserDto;
import dev.sorokin.springbootmvc.service.UserService;
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
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ResponseUserDto> add(@RequestBody @Valid RequestUserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.addUser(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseUserDto> update(@PathVariable @Positive Long id,
                                                  @RequestBody @Valid RequestUpdateUserDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseUserDto> get(@PathVariable @Positive Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUser(id));
    }
}
