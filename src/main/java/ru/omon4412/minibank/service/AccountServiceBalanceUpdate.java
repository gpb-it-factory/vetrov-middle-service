package ru.omon4412.minibank.service;

import ru.omon4412.minibank.dto.ResponseAccountDto;

public interface AccountServiceBalanceUpdate extends AccountService {
    void updateAccount(ResponseAccountDto updatedAccountDto);
}
