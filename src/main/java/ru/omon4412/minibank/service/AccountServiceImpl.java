package ru.omon4412.minibank.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.client.BackendServiceClient;
import ru.omon4412.minibank.dto.NewAccountDto;

@Service
@ConditionalOnProperty(value = "application.accountService.type", havingValue = "backend")
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final BackendServiceClient backendServiceClient;

    @Override
    public void createAccount(Long userId, NewAccountDto newAccountDto) {
        backendServiceClient.createAccount(newAccountDto, userId);
    }
}
