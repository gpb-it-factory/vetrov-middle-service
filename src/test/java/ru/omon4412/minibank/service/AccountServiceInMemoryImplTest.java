package ru.omon4412.minibank.service;

import org.junit.jupiter.api.Test;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.dto.UserRequestDto;
import ru.omon4412.minibank.exception.ConflictException;
import ru.omon4412.minibank.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceInMemoryImplTest {
    private final RegistrationServiceInMemoryImpl registrationServiceInMemory = new RegistrationServiceInMemoryImpl();
    private final AccountServiceInMemoryImpl accountServiceInMemory = new AccountServiceInMemoryImpl(registrationServiceInMemory);


    @Test
    void test_createAccount_whenUserNotRegistered() {
        assertThrows(NotFoundException.class, () -> accountServiceInMemory.createAccount(101010L, null));
    }

    @Test
    void test_createAccount_success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        NewAccountDto newAccountDto = new NewAccountDto();
        newAccountDto.setAccountName("account1");

        registrationServiceInMemory.registerUser(userRequestDto);
        accountServiceInMemory.createAccount(1L, newAccountDto);
    }

    @Test
    void test_createAccount_whenUserAlreadyHasAccount() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        NewAccountDto newAccountDto1 = new NewAccountDto();
        newAccountDto1.setAccountName("account1");
        NewAccountDto newAccountDto2 = new NewAccountDto();
        newAccountDto2.setAccountName("account2");

        registrationServiceInMemory.registerUser(userRequestDto);
        accountServiceInMemory.createAccount(1L, newAccountDto1);

        assertThrows(ConflictException.class, () -> accountServiceInMemory.createAccount(1L, newAccountDto2));
    }
}