package ru.omon4412.minibank.service;

import org.junit.jupiter.api.Test;
import ru.omon4412.minibank.dto.CreateTransferRequestDto;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.dto.TransferResponseDto;
import ru.omon4412.minibank.dto.UserRequestDto;
import ru.omon4412.minibank.exception.ConflictException;
import ru.omon4412.minibank.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceInMemoryImplTest {
    private final RegistrationServiceInMemoryImpl registrationServiceInMemory = new RegistrationServiceInMemoryImpl();
    private final AccountServiceBalanceUpdate accountServiceInMemory = new AccountServiceInMemoryImpl(registrationServiceInMemory);
    private final TransferServiceInMemoryImpl transferServiceInMemory = new TransferServiceInMemoryImpl(registrationServiceInMemory, accountServiceInMemory);

    @Test
    public void test_validTransfer() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        UserRequestDto userRequestDto2 = new UserRequestDto();
        userRequestDto2.setUserId(2L);
        userRequestDto2.setUserName("user2");
        registrationServiceInMemory.registerUser(userRequestDto2);

        accountServiceInMemory.createAccount(1L, new NewAccountDto());
        accountServiceInMemory.createAccount(2L, new NewAccountDto());

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 100.00);
        TransferResponseDto transfer = transferServiceInMemory.transfer(createTransferRequestDto);

        assertNotNull(transfer);
        assertEquals(4900, accountServiceInMemory.getUserAccounts(1L).stream().findFirst().get().getAmount());
        assertEquals(5100, accountServiceInMemory.getUserAccounts(2L).stream().findFirst().get().getAmount());
    }

    @Test
    public void test_notvalidTransfer_whenFirstUserNotRegistered() {
        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 100.00);

        assertThrows(NotFoundException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }

    @Test
    public void test_notvalidTransfer_whenFirstUserDontHaveAccount() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 100.00);

        assertThrows(NotFoundException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }

    @Test
    public void test_notvalidTransfer_whenSecondUserNotRegistered() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        accountServiceInMemory.createAccount(1L, new NewAccountDto());

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 100.00);

        assertThrows(NotFoundException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }

    @Test
    public void test_notvalidTransfer_whenSecondUserDontHaveAccount() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        UserRequestDto userRequestDto2 = new UserRequestDto();
        userRequestDto2.setUserId(2L);
        userRequestDto2.setUserName("user2");
        registrationServiceInMemory.registerUser(userRequestDto2);

        accountServiceInMemory.createAccount(1L, new NewAccountDto());

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 100.00);

        assertThrows(NotFoundException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }

    @Test
    public void test_validTransfer_whenAmountIsZero() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        UserRequestDto userRequestDto2 = new UserRequestDto();
        userRequestDto2.setUserId(2L);
        userRequestDto2.setUserName("user2");
        registrationServiceInMemory.registerUser(userRequestDto2);

        accountServiceInMemory.createAccount(1L, new NewAccountDto());
        accountServiceInMemory.createAccount(2L, new NewAccountDto());

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 0);

        assertThrows(ConflictException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }

    @Test
    public void test_novalidTransfer_whenUserTransferToHimself() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        accountServiceInMemory.createAccount(1L, new NewAccountDto());

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user1", 100);

        assertThrows(ConflictException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }

    @Test
    public void test_validTransfer_whenAmountIsMoreThanBalance() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(1L);
        userRequestDto.setUserName("user1");
        registrationServiceInMemory.registerUser(userRequestDto);

        UserRequestDto userRequestDto2 = new UserRequestDto();
        userRequestDto2.setUserId(2L);
        userRequestDto2.setUserName("user2");
        registrationServiceInMemory.registerUser(userRequestDto2);

        accountServiceInMemory.createAccount(1L, new NewAccountDto());
        accountServiceInMemory.createAccount(2L, new NewAccountDto());

        CreateTransferRequestDto createTransferRequestDto = new CreateTransferRequestDto("user1", "user2", 100000);

        assertThrows(ConflictException.class, () -> transferServiceInMemory.transfer(createTransferRequestDto));
    }
}