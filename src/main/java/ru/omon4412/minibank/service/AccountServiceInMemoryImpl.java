package ru.omon4412.minibank.service;

import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.dto.ResponseAccountDto;
import ru.omon4412.minibank.exception.ConflictException;
import ru.omon4412.minibank.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ConditionalOnProperty(value = "application.accountService.type", havingValue = "inMemory")
public class AccountServiceInMemoryImpl implements AccountService {
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private final RegistrationService registrationService;

    public AccountServiceInMemoryImpl(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void createAccount(Long userId, NewAccountDto newAccountDto) {
        if (registrationService.getUsernameById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (accounts.containsKey(userId)) {
            throw new ConflictException("У пользователя уже есть счет");
        }
        String uuid = UUID.randomUUID().toString();
        Account account = new Account();
        account.setAccountId(uuid);
        account.setOwnerId(userId);
        account.setAccountName(newAccountDto.getAccountName() == null ? "Акционный" : newAccountDto.getAccountName());
        account.setAmount(5000.00);
        accounts.put(userId, account);
    }

    @Override
    public Collection<ResponseAccountDto> getUserAccounts(Long userId) {
        if (registrationService.getUsernameById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        Collection<ResponseAccountDto> accountDtos = new ArrayList<>();
        if (!accounts.containsKey(userId)) {
            return Collections.emptyList();
        }
        accountDtos.add(Account.toResponseAccountDto(accounts.get(userId)));
        return accountDtos;
    }

    @Setter
    static class Account {
        private String accountId;
        private String accountName;
        private double amount;
        private Long ownerId;

        public static ResponseAccountDto toResponseAccountDto(Account account) {
            return ResponseAccountDto.builder()
                    .accountId(account.accountId)
                    .accountName(account.accountName)
                    .amount(account.amount)
                    .build();
        }
    }
}
