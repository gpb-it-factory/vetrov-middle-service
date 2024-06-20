package ru.omon4412.minibank.service;

import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.dto.ResponseAccountDto;

import java.util.Collection;

public interface AccountService {

    void createAccount(Long userId, NewAccountDto newAccountDto);

    Collection<ResponseAccountDto> getUserAccounts(Long userId);
}
