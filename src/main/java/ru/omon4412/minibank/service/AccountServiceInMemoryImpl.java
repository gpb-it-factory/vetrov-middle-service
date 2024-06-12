package ru.omon4412.minibank.service;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.exception.ConflictException;
import ru.omon4412.minibank.exception.NotFoundException;

import java.util.Map;
import java.util.UUID;
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
        account.setAccountName(newAccountDto.getAccountName());
        account.setAmount(5000L);
        accounts.put(userId, account);
    }
}

@Data
class Account {
    private String accountId;
    private String accountName;
    private long amount;
    private Long ownerId;
}
