package ru.omon4412.minibank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.omon4412.minibank.dto.UserIdResponseDto;
import ru.omon4412.minibank.dto.UserRequestDto;
import ru.omon4412.minibank.service.RegistrationService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        registrationService.registerUser(userRequestDto);
    }

    @GetMapping("/{username}")
    ResponseEntity<UserIdResponseDto> getUserIdByUserName(@PathVariable("username") String username) {
        return ResponseEntity.ok(registrationService.getUserIdByUsername(username));
    }
}
