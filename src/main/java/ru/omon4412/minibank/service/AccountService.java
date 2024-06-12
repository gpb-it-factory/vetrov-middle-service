package ru.omon4412.minibank.service;

import ru.omon4412.minibank.dto.NewAccountDto;

public interface AccountService {

    void createAccount(Long userId, NewAccountDto newAccountDto);
}
