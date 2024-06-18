package ru.omon4412.minibank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.service.AccountService;

@RestController
@RequestMapping("/users/{id}/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createAccount(@PathVariable("id") Long userId,
                              @Valid @RequestBody NewAccountDto newAccountDto) {
        accountService.createAccount(userId, newAccountDto);
    }
}
